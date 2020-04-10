package com.poliplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Inscripcion {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 16, unique = true)
    private UUID uuid = UUID.randomUUID();

    //usuario
    @ManyToMany
    private List<Seccion> secciones;

    private Date createdAt;


    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

}
