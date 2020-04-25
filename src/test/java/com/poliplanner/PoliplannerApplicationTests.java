package com.poliplanner;

import com.poliplanner.data.*;
import com.poliplanner.domain.model.Aula;
import com.poliplanner.domain.model.Horario;
import com.poliplanner.domain.model.Materia;
import com.poliplanner.domain.model.Seccion;
import com.poliplanner.excel.ExcelHelper;
import com.poliplanner.excel.ExcelReader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class PoliplannerApplicationTests {
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    CarreraRepository carreraRepository;
    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    SeccionRepository seccionRepository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    ExcelReader excelReader;


    @SneakyThrows
    @Test
    void contextLoads() {
        final ExcelReader excelReader = new ExcelReader();

        List<String> asignaturas = new ArrayList<String >(new LinkedHashSet<String>(List.of("Algebra Lineal","Algebra Lineal","Algebra Lineal","Algoritmos y Estructuras de Datos I","Algoritmos y Estructuras de Datos I","Algoritmos y Estructuras de Datos II","Algoritmos y Estructuras de Datos III","Algoritmos y Estructuras de Datos III","Bases de Datos I (*)","Bases de Datos II (*)","Cálculo I","Cálculo I","Cálculo I","Cálculo I","Cálculo I","Cálculo I","Cálculo I","Cálculo I","Cálculo II","Cálculo II","Cálculo II","Cálculo II","Cálculo II","Cálculo II","Cálculo III","Cálculo III","Cálculo III","Cálculo III","Cálculo III","Contabilidad","Contabilidad","Contabilidad","Contabilidad (*)","Diseño de Compiladores","Economía y Finanzas","Economía y Finanzas","Electiva 1 - Auditoría y Tecnología de la Información (*)","Electiva 1 - Biocomputación","Electiva 1 - Ciberseguridad","Electiva 1 - Cloud Computing","Electiva 1 - Data Science","Electiva 1 - Datamining (*)","Electiva 1 - Desempeño y Seguridad de las Redes","Electiva 1 - Gestión de Proyectos","Electiva 1 - Interacción Humano - Computador (*)","Electiva 1 - Procesamiento Digital de Imágenes","Electiva 1 - Programación Competitiva (*)","Electiva 1 - Programación Web Back-End","Electiva 1 - Programación Web Front-End (*)","Electiva 1 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 2 - Ciberseguridad","Electiva 2 - Auditoría y Tecnología de la Información (*)","Electiva 2 - Biocomputación","Electiva 2 - Cloud Computing","Electiva 2 - Data Science","Electiva 2 - Datamining (*)","Electiva 2 - Desempeño y Seguridad de las Redes","Electiva 2 - Gestión de Proyectos","Electiva 2 - Interacción Humano - Computador (*)","Electiva 2 - Procesamiento Digital de Imágenes","Electiva 2 - Programación Competitiva (*)","Electiva 2 - Programación Web Back-End","Electiva 2 - Programación Web Front-End (*)","Electiva 2 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 3 - Auditoría y Tecnología de la Información (*)","Electiva 3 - Biocomputación","Electiva 3 - Ciberseguridad","Electiva 3 - Cloud Computing","Electiva 3 - Data Science","Electiva 3 - Datamining (*)","Electiva 3 - Desempeño y Seguridad de las Redes","Electiva 3 - Gestión de Proyectos","Electiva 3 - Interacción Humano - Computador (*)","Electiva 3 - Procesamiento Digital de Imágenes","Electiva 3 - Programación Competitiva (*)","Electiva 3 - Programación Web Back-End","Electiva 3 - Programación Web Front-End (*)","Electiva 3 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 4 - Auditoría y Tecnología de la Información (*)","Electiva 4 - Biocomputación","Electiva 4 - Ciberseguridad","Electiva 4 - Cloud Computing","Electiva 4 - Data Science","Electiva 4 - Datamining (*)","Electiva 4 - Desempeño y Seguridad de las Redes","Electiva 4 - Gestión de Proyectos","Electiva 4 - Interacción Humano - Computador (*)","Electiva 4 - Procesamiento Digital de Imágenes","Electiva 4 - Programación Competitiva (*)","Electiva 4 - Programación Web Back-End","Electiva 4 - Programación Web Front-End (*)","Electiva 4 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 5 - Auditoría y Tecnología de la Información (*)","Electiva 5 - Biocomputación","Electiva 5 - Ciberseguridad","Electiva 5 - Cloud Computing","Electiva 5 - Data Science","Electiva 5 - Datamining (*)","Electiva 5 - Desempeño y Seguridad de las Redes","Electiva 5 - Gestión de Proyectos","Electiva 5 - Interacción Humano - Computador (*)","Electiva 5 - Procesamiento Digital de Imágenes","Electiva 5 - Programación Competitiva (*)","Electiva 5 - Programación Web Back-End","Electiva 5 - Programación Web Front-End (*)","Electiva 5 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 6 - Auditoría y Tecnología de la Información (*)","Electiva 6 - Biocomputación","Electiva 6 - Ciberseguridad","Electiva 6 - Cloud Computing","Electiva 6 - Data Science","Electiva 6 - Datamining (*)","Electiva 6 - Desempeño y Seguridad de las Redes","Electiva 6 - Gestión de Proyectos","Electiva 6 - Interacción Humano - Computador (*)","Electiva 6 - Procesamiento Digital de Imágenes","Electiva 6 - Programación Competitiva (*)","Electiva 6 - Programación Web Back-End","Electiva 6 - Programación Web Front-End (*)","Electiva 6 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Electiva 7 - Auditoría y Tecnología de la Información (*)","Electiva 7 - Biocomputación","Electiva 7 - Ciberseguridad","Electiva 7 - Cloud Computing","Electiva 7 - Data Science","Electiva 7 - Datamining (*)","Electiva 7 - Desempeño y Seguridad de las Redes","Electiva 7 - Gestión de Proyectos","Electiva 7 - Interacción Humano - Computador (*)","Electiva 7 - Procesamiento Digital de Imágenes","Electiva 7 - Programación Competitiva (*)","Electiva 7 - Programación Web Back-End","Electiva 7 - Programación Web Front-End (*)","Electiva 7 - Tecnología Emergente en Telecomunicaciones y Redes (*)","Emprendedorismo","Emprendedorismo","Emprendedorismo","Emprendedorismo","Emprendedorismo","Emprendedorismo (*)","Estructura de los Lenguajes","Expresión Oral y Escrita","Expresión Oral y Escrita","Expresión Oral y Escrita","Física 1","Física 1","Física 1","Física 1","Física 1","Física 1","Física 2","Física 2","Física 2","Física 2","Física 2","Física 3","Física 3","Física 3","Física 4","Física 4","Física 4","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Fundamentos de Matemática","Gestión de Centro de Cómputos","Ingeniería de Software I (*)","Ingeniería de Software II","Ingeniería de Software III (*)","Inglés","Inglés","Inglés","Inglés","Investigación de Operaciones I","Investigación de Operaciones I (*)","Investigación de Operaciones II","Lenguajes de Programación I","Lenguajes de Programación II","Lenguajes de Programación III","Lógica para Ciencias de la Computación","Matemática Aplicada","Matemática Discreta","Matemática Discreta","Métodos Numéricos","Métodos Numéricos (*)","Optativa 1 - Administración Gubernamental","Optativa 1 - E - Business","Optativa 1 - E - Business","Optativa 1 - E - Business","Optativa 1 - Ecología y Desarrollo Sustentable","Optativa 1 - Merchandising (*)","Optativa 1 - Metodología de la Investigación","Optativa 1 - Metodología de la Investigación","Optativa 1 - Metodología de la Investigación","Optativa 1 - Metodología de la Investigación (*)","Optativa 1 - Metodología de la Investigación (*)","Optativa 1 - Políticas Públicas y de Negocios (*)","Optativa 1 - Realidad Nacional","Optativa 2 - Ecología y Desarrollo Sustentable","Optativa 2 - Merchandising (*)","Optativa 2 - Metodología de la Investigación","Optativa 2 - Metodología de la Investigación","Optativa 2 - Metodología de la Investigación","Optativa 2 - Metodología de la Investigación (*)","Optativa 2 - Metodología de la Investigación (*)","Optativa 2 - Políticas Públicas y de Negocios (*)","Optativa 2 - Realidad Nacional","Optativa 3 - Didáctica Universitaria","Optativa 3 - Ecología y Desarrollo Sustentable","Optativa 3 - Marketing","Optativa 3 - Merchandising (*)","Optativa 3 - Metodología de la Investigación","Optativa 3 - Metodología de la Investigación","Optativa 3 - Metodología de la Investigación","Optativa 3 - Metodología de la Investigación (*)","Optativa 3 - Metodología de la Investigación (*)","Optativa 3 - Políticas Públicas y de Negocios (*)","Organización y Arquitectura de Computadoras I","Organización y Arquitectura de Computadoras II","Probabilidades y Estadística","Probabilidades y Estadística","Probabilidades y Estadística","Probabilidades y Estadística","Redes de Computadoras I","Redes de Computadoras II","Sistemas Distribuidos","Sistemas Operativos","Técnicas de Organización y Métodos","Técnicas de Organización y Métodos","Técnicas de Organización y Métodos","Técnicas de Organización y Métodos","Técnicas de Organización y Métodos","Técnicas de Organización y Métodos").stream().map(a -> ExcelHelper.quitarAcentos(a)).collect(Collectors.toList())));
        List<Materia> materias = materiaRepository.findByCarrera(carreraRepository.findByCodigo("IIN"));
        materias.sort((m1, m2)-> ExcelHelper.quitarAcentos(m1.getNombre()).compareTo(ExcelHelper.quitarAcentos(m2.getNombre())));

        Map<String, Materia> map = ExcelHelper.mapAsignaturaToMateria(asignaturas, materias);
        assert asignaturas.size() == map.size();
    }

    @SneakyThrows
    @Test
    @Transactional
    void test(){
        File file = new ClassPathResource("horario.xls").getFile();
        excelReader.loadExcel(file);

        Horario horario = horarioRepository.findAll().iterator().next();
        List<Seccion> secciones = seccionRepository.findByMateria_Carrera_CodigoInAndHorario_Uuid(List.of("IIN"), horario.getUuid());
        assert secciones.size() == 238;
    }
}
