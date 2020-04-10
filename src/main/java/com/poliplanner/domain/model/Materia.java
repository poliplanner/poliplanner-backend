package com.poliplanner.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne
    private Carrera carrera;
    @NotNull
    private String nombre;

    private Integer creditos;

    private Float porcentajeCreditosRequeridos;

    private Integer semestre;
}
