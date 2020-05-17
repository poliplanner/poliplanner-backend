package com.poliplanner.domain.pojo;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class Horario {
    private UUID uuid;

    private Date fechaPublicacion;

    private String nombre;

    private String descripcion;
}
