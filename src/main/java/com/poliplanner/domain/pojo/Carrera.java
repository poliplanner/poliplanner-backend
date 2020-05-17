package com.poliplanner.domain.pojo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Carrera {
    private UUID uuid;
    private String codigo;
    private String nombre;
    private Integer cantidadSemestres;
}
