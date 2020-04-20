package com.poliplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seccion {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 16, unique = true)
    private UUID uuid = UUID.randomUUID();

    private String codigo;

    private String nombre;

    private boolean soloConFirma = false;

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

    public String getNombre(){
        return nombre != null? nombre: materia.getNombre();
    }
}
