package com.poliplanner.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
