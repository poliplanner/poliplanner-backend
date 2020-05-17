package com.poliplanner.domain.pojo;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class Examen {
    private UUID uuid;

    private Date fecha;
    private Date fechaRevision;

    private Aula aula;

    private Tipo tipo;

    public static enum Tipo {
        PRIMER_PARCIAL, SEGUNDO_PARCIAL, PRIMER_FINAL, SEGUNDO_FINAL;
    }
}
