package com.poliplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poliplanner.domain.enums.Dia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Clase {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 16, unique = true)
    private UUID uuid = UUID.randomUUID();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
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
