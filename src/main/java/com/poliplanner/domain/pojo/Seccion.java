package com.poliplanner.domain.pojo;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Seccion {
    private UUID uuid = UUID.randomUUID();

    private String codigo;

    private String nombre;

    private boolean soloConFirma = false;

    private Horario horario;

    private Materia materia;

    private List<String> profesores;

    private List<Examen> examenes;

    private List<Clase> clases;
}
