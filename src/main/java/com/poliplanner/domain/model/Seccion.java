package com.poliplanner.domain.model;

import java.util.List;

public class Seccion {
    String codigo;
    Horario horario;
    Materia materia;
    List<String> profesores;
    List<Examen> examenes;
    List<Clase> clases;
}
