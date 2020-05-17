package com.poliplanner.domain.pojo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Materia {
    private UUID uuid = UUID.randomUUID();
    private Carrera carrera;
    private String nombre;
    private Integer creditos;
    private Float porcentajeCreditosRequeridos;
    private Integer semestre;
}
