package com.poliplanner.domain.pojo;

import com.poliplanner.domain.enums.Dia;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Clase {
    private UUID uuid;

    private Tipo tipo;

    private Dia dia;

    private String inicio;
    private String fin;

    private Aula aula;


    @AllArgsConstructor
    public static enum Tipo {
        CLASE("Clase"), LAB ("Laboratorio");
        String name;
    }

}
