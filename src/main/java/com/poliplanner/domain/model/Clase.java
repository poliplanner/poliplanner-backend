package com.poliplanner.domain.model;

import com.poliplanner.domain.enums.Dia;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Clase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull
    private Dia dia;

    @NotNull
    @Pattern(regexp="^([0-2][0-9])(:)([0-5][0-9])$", message="Debe que tener formato hh:MM")
    private String inicio;
    @NotNull
    @Pattern(regexp="^([0-2][0-9])(:)([0-5][0-9])$", message="Debe que tener formato hh:MM")
    private String fin;

    @ManyToOne
    private Aula aula;


    @AllArgsConstructor
    public static enum Tipo {
        CLASE("Clase"), LAB ("Laboratorio");
        String name;
    }

}
