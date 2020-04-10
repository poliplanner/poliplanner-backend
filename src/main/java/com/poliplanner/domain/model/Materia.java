package com.poliplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Materia {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 16, unique = true)
    private UUID uuid = UUID.randomUUID();
    @NotNull
    @ManyToOne
    private Carrera carrera;
    @NotNull
    private String nombre;

    private Integer creditos;

    private Float porcentajeCreditosRequeridos;

    private Integer semestre;
}
