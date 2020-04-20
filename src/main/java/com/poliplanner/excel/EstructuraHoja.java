package com.poliplanner.excel;

import com.poliplanner.domain.model.Examen;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EstructuraHoja {
    private Integer subHeaderRowPos = null;
    private Integer headerRowPos = null;

    private Integer asignaturaColumn =null;
    private Integer codigoCarreraColumn =null;
    private Integer enfasisColumn =null;
    private Integer seccionColumn =null;
    private Integer apellidoProfesoresColumn = null;
    private Integer nombreProfesoresColumn = null;

    private Integer lunesColumn = null;
    private Integer lunesAulaColumn = null;

    private Integer martesColumn = null;
    private Integer martesAulaColumn = null;

    private Integer miercolesColumn = null;
    private Integer miercolesAulaColumn = null;

    private Integer juevesColumn = null;
    private Integer juevesAulaColumn = null;

    private Integer viernesColumn = null;
    private Integer viernesAulaColumn = null;

    private Integer sabadoColumn = null;
    private Integer sabadoAulaColumn = null;
    private Integer fechaClaseSabadosColumn = null;

    private Integer primerParcialFechaColumn = null;
    private Integer primerParcialHoraColumn = null;
    private Integer primerParcialAulaColumn = null;

    private Integer segundoParcialFechaColumn = null;
    private Integer segundoParcialHoraColumn = null;
    private Integer segundoParcialAulaColumn = null;

    private Integer primerFinalFechaColumn = null;
    private Integer primerFinalHoraColumn = null;
    private Integer primerFinalAulaColumn = null;
    private Integer primerFinalRevisionFechaColumn = null;
    private Integer primerFinalRevisionHoraColumn = null;

    private Integer segundoFinalFechaColumn = null;
    private Integer segundoFinalHoraColumn = null;
    private Integer segundoFinalAulaColumn = null;
    private Integer segundoFinalRevisionFechaColumn = null;
    private Integer segundoFinalRevisionHoraColumn = null;

    public EstructuraHoja(Sheet sheet){
        Row row = null;
        Cell cell = null;
        int rowPos = 0;
        while (cell == null ||
                !(cell.getCellTypeEnum().equals(CellType.STRING) && cell.getStringCellValue().startsWith("Item"))) {
            row = sheet.getRow(rowPos);
            cell = row.getCell(0);
            rowPos++;
        }
        setHeaderRowPos(rowPos - 2);
        setSubHeaderRowPos(rowPos - 1);

        Row headerRow = sheet.getRow(getHeaderRowPos());
        Row subHeaderRow = sheet.getRow(getSubHeaderRowPos());

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String subHeader = subHeaderRow.getCell(i).getStringCellValue();

            if (subHeader.equals("Asignatura"))
                setAsignaturaColumn(i);
            else if (subHeader.equals("Sigla carrera"))
                setCodigoCarreraColumn(i);
            else if (subHeader.equals("Enfasis"))
                setEnfasisColumn(i);
            else if (subHeader.equals("Sección"))
                setSeccionColumn(i);
            else if (subHeader.equals("Apellido"))
                setApellidoProfesoresColumn(i);
            else if (subHeader.equals("Nombre"))
                setNombreProfesoresColumn(i);


            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("1er. Parcial"))
                setPrimerParcialFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("1er. Parcial"))
                setPrimerParcialHoraColumn(i);
            else if (subHeader.equals("AULA") &&
                    headerRow.getCell(i - 2).getStringCellValue().equals("1er. Parcial"))
                setPrimerParcialAulaColumn(i);


            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("2do. Parcial"))
                setSegundoParcialFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("2do. Parcial"))
                setSegundoParcialHoraColumn(i);
            else if (subHeader.equals("AULA") &&
                    headerRow.getCell(i - 2).getStringCellValue().equals("2do. Parcial"))
                setSegundoParcialHoraColumn(i);


            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("1er. Final"))
                setPrimerFinalFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("1er. Final"))
                setPrimerFinalHoraColumn(i);
            else if (subHeader.equals("AULA") &&
                    headerRow.getCell(i - 2).getStringCellValue().equals("1er. Final"))
                setPrimerFinalAulaColumn(i);
            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("Revisión") &&
                    headerRow.getCell(i - 3).getStringCellValue().equals("1er. Final"))
                setPrimerFinalRevisionFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("Revisión") &&
                    headerRow.getCell(i - 4).getStringCellValue().equals("1er. Final"))
                setPrimerFinalRevisionHoraColumn(i);


            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("2do. Final"))
                setSegundoFinalFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("2do. Final"))
                setSegundoFinalHoraColumn(i);
            else if (subHeader.equals("AULA") &&
                    headerRow.getCell(i - 2).getStringCellValue().equals("2do. Final")
                    && !subHeaderRow.getCell(i + 2).getStringCellValue().equals("AULA"))
                setSegundoFinalAulaColumn(i);
            else if (subHeader.equals("Día") &&
                    headerRow.getCell(i).getStringCellValue().equals("Revisión") &&
                    headerRow.getCell(i - 3).getStringCellValue().equals("2do. Final"))
                setSegundoFinalRevisionFechaColumn(i);
            else if (subHeader.equals("Hora") &&
                    headerRow.getCell(i - 1).getStringCellValue().equals("Revisión") &&
                    headerRow.getCell(i - 4).getStringCellValue().equals("2do. Final"))
                setSegundoFinalRevisionHoraColumn(i);


            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Lunes"))
                setLunesAulaColumn(i);
            else if (subHeader.equals("Lunes"))
                setLunesColumn(i);

            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Martes"))
                setMartesAulaColumn(i);
            else if (subHeader.equals("Martes"))
                setMartesColumn(i);

            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Miércoles"))
                setMiercolesAulaColumn(i);
            else if (subHeader.equals("Miércoles"))
                setMiercolesColumn(i);

            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Jueves"))
                setJuevesAulaColumn(i);
            else if (subHeader.equals("Jueves"))
                setJuevesColumn(i);

            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Viernes"))
                setViernesAulaColumn(i);
            else if (subHeader.equals("Viernes"))
                setViernesColumn(i);

            else if (subHeader.equals("AULA") &&
                    subHeaderRow.getCell(i + 1).getStringCellValue().equals("Sábado"))
                setSabadoAulaColumn(i);
            else if (subHeader.equals("Sábado"))
                setSabadoColumn(i);
            else if (subHeader.equals("Fechas de clases de sábados (Turno Noche)"))
                setFechaClaseSabadosColumn(i);
        }

    }
    
    public ExamenColumns getColumnasTipoExamen(Examen.Tipo tipo){
        Integer fechaColumn = null, horaColumn = null, aulaColumn = null, revisionFechaColumn = null, revisionHoraColumn = null;
        switch (tipo){
            case PRIMER_PARCIAL:
                fechaColumn = getPrimerParcialFechaColumn();
                horaColumn = getPrimerParcialHoraColumn();
                aulaColumn = getPrimerParcialAulaColumn();
                break;
            case SEGUNDO_PARCIAL:
                fechaColumn = getSegundoParcialFechaColumn();
                horaColumn = getSegundoParcialHoraColumn();
                aulaColumn = getSegundoParcialAulaColumn();
                break;
            case PRIMER_FINAL:
                fechaColumn = getPrimerFinalFechaColumn();
                horaColumn = getPrimerFinalHoraColumn();
                aulaColumn = getPrimerFinalAulaColumn();

                revisionFechaColumn = getPrimerFinalRevisionFechaColumn();
                revisionHoraColumn = getPrimerFinalRevisionHoraColumn();
                break;
            case SEGUNDO_FINAL:
                fechaColumn = getSegundoFinalFechaColumn();
                horaColumn = getSegundoFinalHoraColumn();
                aulaColumn = getSegundoFinalAulaColumn();

                revisionFechaColumn = getSegundoFinalRevisionFechaColumn();
                revisionHoraColumn = getSegundoFinalRevisionHoraColumn();
                break;
        }
        return new ExamenColumns(fechaColumn,horaColumn,aulaColumn,revisionFechaColumn,revisionHoraColumn);
    }
    
    
}
