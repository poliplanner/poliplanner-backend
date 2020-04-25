package com.poliplanner.excel;

import com.poliplanner.data.AulaRepository;
import com.poliplanner.data.MateriaRepository;
import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.enums.Dia;
import com.poliplanner.domain.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelCarreraReader {

    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    SeccionRepository seccionRepository;

    public void loadSecciones(Sheet sheet, Horario horario, Carrera carrera){
        EstructuraHoja hoja = new EstructuraHoja(sheet);

        List<String> asignaturas = listAsignaturas(sheet,hoja);
        List<Materia> materias = materiaRepository.findByCarrera(carrera);
        materias.sort((m1, m2)-> ExcelHelper.quitarAcentos(m1.getNombre()).compareTo(ExcelHelper.quitarAcentos(m2.getNombre())));
        Map<String,Materia> asignaturaMateriaMap = ExcelHelper.mapAsignaturaToMateria(asignaturas, materias);

        List<String> aulasString = listAulas(sheet,hoja);
        List<Aula> aulas = aulaRepository.saveFindByCodigo(aulasString);
        Map<String, Aula> codigoAulaMap = ExcelHelper.mapCodigoAulaToAula(aulas);


        List<Seccion> secciones = new ArrayList<>();

        int i = hoja.getSubHeaderRowPos() + 1;
        Row row = sheet.getRow(i);

        while(row != null && row.getCell(hoja.getAsignaturaColumn()) != null){
            Seccion seccion = new Seccion();

            String asignatura = row.getCell(hoja.getAsignaturaColumn()).getStringCellValue();

            seccion.setNombre(asignatura);
            Materia materia = asignaturaMateriaMap.get(ExcelHelper.quitarAcentos(asignatura));
            seccion.setMateria(materia);
            if(asignatura.contains("(*)"))
                seccion.setSoloConFirma(true);

            String codigoSeccion = row.getCell(hoja.getSeccionColumn()).getStringCellValue();
            seccion.setCodigo(codigoSeccion);
            seccion.setProfesores(ExcelSeccionHelper.readProfesores(hoja, row));
            seccion.setClases(ExcelSeccionHelper.readClases(hoja,row, codigoAulaMap));
            seccion.setExamenes(ExcelSeccionHelper.readExamenes(hoja, row, codigoAulaMap, seccion.isSoloConFirma()));

            seccion.setHorario(horario);

            secciones.add(seccion);

            i +=1;
            row = sheet.getRow(i);
        }

        List<Seccion> seccionList = (List<Seccion>)seccionRepository.saveAll(secciones);
        assert seccionList.size() == secciones.size();
    }


    public List<String> listAsignaturas(Sheet sheet, EstructuraHoja hoja){
        int i = hoja.getSubHeaderRowPos() + 1;
        Row row = sheet.getRow(i);
        Set<String> asignaturas = new LinkedHashSet<>();
        while(row != null && row.getCell(hoja.getAsignaturaColumn()) != null) {
            asignaturas.add(row.getCell(hoja.getAsignaturaColumn()).getStringCellValue());
            i +=1;
            row = sheet.getRow(i);
        }

        return new ArrayList<>(asignaturas);
    }

    public List<String> listAulas(Sheet sheet, EstructuraHoja hoja){
        int i = hoja.getSubHeaderRowPos() + 1;
        Row row = sheet.getRow(i);
        List<Integer> aulaColumns = List.of(hoja.getLunesAulaColumn(), hoja.getMartesAulaColumn(),
                hoja.getMiercolesAulaColumn(), hoja.getJuevesAulaColumn(),
                hoja.getViernesAulaColumn(), hoja.getSabadoAulaColumn(), hoja.getPrimerParcialAulaColumn(),
                hoja.getSegundoParcialAulaColumn(), hoja.getPrimerFinalAulaColumn(), hoja.getSegundoFinalAulaColumn())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Set<String> aulas = new LinkedHashSet<>();
        while(row != null && row.getCell(hoja.getAsignaturaColumn()) != null) {
            Row finalRow = row;

            List<String> aulasRow = aulaColumns
                    .stream()
                    .map(posColumn -> finalRow.getCell(posColumn).getStringCellValue().trim())
                    .filter(aula -> !aula.isEmpty())
                    .collect(Collectors.toList());
            aulas.addAll(aulasRow);
            i +=1;
            row = sheet.getRow(i);
        }

        return new ArrayList<>(aulas);
    }
}
