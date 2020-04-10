package com.poliplanner.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
public class Seccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String codigo;

    @ManyToOne
    private Horario horario;

    @ManyToOne
    private Materia materia;

    @ElementCollection
    private List<String> profesores;

    @ManyToMany
    private List<Examen> examenes;
    @ManyToMany
    private List<Clase> clases;
}
