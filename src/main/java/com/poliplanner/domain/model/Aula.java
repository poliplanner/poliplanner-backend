package com.poliplanner.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Aula {
    @Id
    private Long id;
    private String codigo;
}
