package com.poliplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Examen {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 16, unique = true)
    private UUID uuid = UUID.randomUUID();

    private Date fecha;
    private Date fechaRevision;

    @ManyToOne
    private Aula aula;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public static enum Tipo {
        PRIMER_PARCIAL, SEGUNDO_PARCIAL, PRIMER_FINAL, SEGUNDO_FINAL;
    }
}
