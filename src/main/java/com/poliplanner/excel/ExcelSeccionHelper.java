package com.poliplanner.excel;

import com.poliplanner.domain.enums.Dia;
import com.poliplanner.domain.model.Aula;
import com.poliplanner.domain.model.Clase;
import com.poliplanner.domain.model.Examen;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelSeccionHelper {
    // define el tamano de la cadena horario, se espera que tenga
    // un tamano de 11 debido a "hh:mm-hh:mm"
    private static int HORARIO_LENGTH = 11;

    public static List<String> readProfesores(EstructuraHoja estructuraHoja, Row row){
        Cell nombreProfesoresCell = row.getCell(estructuraHoja.getNombreProfesoresColumn());
        String nombreProfesores = "";
        if(nombreProfesoresCell != null)
            nombreProfesores = nombreProfesoresCell.getStringCellValue();

        Cell apellidoProfesoresCell = row.getCell(estructuraHoja.getApellidoProfesoresColumn());
        String apellidoProfesores = "";
        if(apellidoProfesoresCell != null)
            apellidoProfesores = apellidoProfesoresCell.getStringCellValue();

        String[] nombreProfesoresArray = nombreProfesores.split("\n");
        String[] apellidoProfesoresArray = apellidoProfesores.split("\n");

        List<String> profesores = new ArrayList<>();
        for(int i = 0 ; i < nombreProfesoresArray.length && i < apellidoProfesoresArray.length ; i++){
            profesores.add(nombreProfesoresArray[i] + " "+ apellidoProfesoresArray[i]+"\n");
        }
        return profesores;
    }


    public static List<Clase> readClases(EstructuraHoja eh, Row row, Map<String, Aula> codigoAulaMap){

        List<Clase> claseList = new ArrayList<>();
        for(Dia dia: Dia.values()){
            claseList.addAll(getClasesEnDia(eh,row, dia,codigoAulaMap));
        }
        claseList.removeIf(Objects::isNull);
        return claseList;
    }

    private static List<Clase> getClasesEnDia(EstructuraHoja eh, Row row, Dia dia, Map<String, Aula> codigoAulaMap){
        List<Clase> claseList = new ArrayList<>();

        ClaseColumns claseColumns = eh.getColumnasClaseDia(dia);
        Cell horarioCell = row.getCell(claseColumns.getHorarioColumn());
        if(horarioCell == null)
            return claseList;
        String horarioString = horarioCell.getStringCellValue()
                .toLowerCase(Locale.ENGLISH)
                .replaceAll(" ", "")
                .replaceAll("\n", "")
                .replaceAll("ab","")
                .replaceAll("[.]",":")
                .replaceAll(" ", "");
        if(horarioString.isEmpty())
            return claseList;

        String aulaString = null;

        if(claseColumns.getAulaColumn() != null && row.getCell(claseColumns.getAulaColumn()) != null){
            aulaString = row.getCell(claseColumns.getAulaColumn()).getStringCellValue()
                    .replaceAll(" ","")
                    .replaceAll("\n","")
                    .replaceAll("Lab","")
                    .replaceAll(" ", "");
        }

        // si hay algun laboratorio en este horario
        if(horarioString.contains("(l")){
            int horarioLabEndIndex = horarioString.indexOf("(l");
            int horarioLabStartIndex = horarioLabEndIndex - HORARIO_LENGTH;

            String horarioLab = horarioString.substring(horarioLabStartIndex, horarioLabEndIndex);
            Clase claseLab = new Clase();
            claseLab.setDia(dia);
            claseLab.setTipo(Clase.Tipo.LAB);
            String[] split = horarioLab.split("-");
            claseLab.setInicio(split[0]);
            claseLab.setFin(split[1]);

            horarioString = horarioString
                    .replace(horarioLab,"");
        }

        horarioString = horarioString
                .replaceAll("[(l)]","")
                .replaceAll("[a-z]", "");

        if(!horarioString.isEmpty()){
            // debido a que una materia puede tener varias clases en un mismo dia,
            // obtener los horarios de todas esas clases
            List<String> horariosClase = ExcelHelper.splitStringBySize(horarioString, HORARIO_LENGTH);
            for(String horarioClase : horariosClase){
                Clase clase = new Clase();
                clase.setDia(dia);
                clase.setTipo(Clase.Tipo.CLASE);

                if(aulaString != null)
                    clase.setAula(codigoAulaMap.get(aulaString));

                String[] split = horarioClase.split("-");
                clase.setInicio(split[0]);
                clase.setFin(split[1]);

                claseList.add(clase);
            }
        }

        return claseList;
    }

    public static List<Examen> readExamenes(EstructuraHoja e, Row row, Map<String, Aula> codigoAulaMap, boolean soloConFirma){
        List<Examen> examenList = new ArrayList<>();
        if(!soloConFirma){
            examenList.add(getExamen(e,row, codigoAulaMap,Examen.Tipo.PRIMER_PARCIAL));
            examenList.add(getExamen(e,row, codigoAulaMap, Examen.Tipo.SEGUNDO_PARCIAL));
        }
        examenList.add(getExamen(e,row, codigoAulaMap, Examen.Tipo.PRIMER_FINAL));
        examenList.add(getExamen(e,row, codigoAulaMap, Examen.Tipo.SEGUNDO_FINAL));

        examenList.removeIf(Objects::isNull);
        return examenList;
    }

    private static Examen getExamen(EstructuraHoja e, Row row, Map<String, Aula> codigoAulaMap, Examen.Tipo tipo){
        ExamenColumns examenColumns = e.getColumnasTipoExamen(tipo);

        Examen examen = new Examen();
        examen.setTipo(tipo);

        try{
            Date examenDate = getDateFromFechaHora(row, examenColumns.getFechaColumn(), examenColumns.getHoraColumn());
            examen.setFecha(examenDate);

        }catch (ParseException exp){
            return null;
        }
        if(examenColumns.getAulaColumn() != null && row.getCell(examenColumns.getAulaColumn()) != null) {
            String aula = row.getCell(examenColumns.getAulaColumn()).getStringCellValue();
            examen.setAula(codigoAulaMap.get(aula.trim()));
        }


        if(examenColumns.getRevisionFechaColumn() != null && examenColumns.getRevisionHoraColumn() != null){
            try {
                examen.setFechaRevision(
                        getDateFromFechaHora(row,
                                examenColumns.getRevisionFechaColumn(),
                                examenColumns.getRevisionHoraColumn()));
            }catch (Exception exp){}
        }
        return examen;
    }

    private static Date getDateFromFechaHora(Row row, Integer fechaColumn, Integer horaColumn) throws ParseException {

        Calendar fechaHoraExamen = Calendar.getInstance(Locale.ENGLISH);

        Cell fechaCell = row.getCell(fechaColumn);
        String fecha = fechaCell.getStringCellValue()
                .replaceAll("[ a-zA-Z]","");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        fechaHoraExamen.setTime(sdf.parse(fecha));

        Cell horaCell = row.getCell(horaColumn);
        int hora = 0;
        int minuto = 0;
        if(horaCell.getCellTypeEnum().equals(CellType.STRING)){
            String[] horaMinuto = horaCell.getStringCellValue()
                    .replaceAll(" ", "")
                    .replaceAll("[.]", ":")
                    .split(":");

            hora = Integer.parseInt(horaMinuto[0]);
            minuto = Integer.parseInt(horaMinuto[1]);
        }else {
            Date timeDate = horaCell.getDateCellValue();
            if(timeDate != null){
                Calendar horaCalendar = Calendar.getInstance(Locale.ENGLISH);
                horaCalendar.setTime(timeDate);

                hora = horaCalendar.get(Calendar.HOUR_OF_DAY);
                minuto = horaCalendar.get(Calendar.MINUTE);
            }
        }

        fechaHoraExamen.set(Calendar.HOUR_OF_DAY, hora);
        fechaHoraExamen.set(Calendar.MINUTE, minuto);
        return fechaHoraExamen.getTime();
    }


}
