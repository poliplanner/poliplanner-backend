package com.poliplanner.excel;

import com.poliplanner.data.*;
import com.poliplanner.domain.enums.Dia;
import com.poliplanner.domain.model.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelReader {
    @Autowired
    SeccionRepository seccionRepository;
    @Autowired
    CarreraRepository carreraRepository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    ExcelCarreraReader carreraReader;

    public void loadExcel(String fileLocation) throws FileNotFoundException, IOException {
        this.loadExcel(new File(fileLocation));
    }

    public void loadExcel(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        readHSSFWorkbook(fis);
    }

    private void readHSSFWorkbook(FileInputStream fis) throws IOException {

        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(fis);

            Horario horario = new Horario();
            horario.setNombre("Prueba");
            horario = horarioRepository.save(horario);

            Iterable<Carrera> carreras = carreraRepository.findAll();
            for(Carrera carrera : carreras){
                String codigoCarrera = carrera.getCodigo();
                if(codigoCarrera.contains("-")){
                    codigoCarrera = codigoCarrera.substring(0,codigoCarrera.indexOf('-'));
                }

                HSSFSheet sheet = workbook.getSheet(codigoCarrera);
                carreraReader.loadSecciones(sheet, horario, carrera);
            }

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

}
