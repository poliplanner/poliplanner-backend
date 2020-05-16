package com.poliplanner.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Usuario {
    @Id
    private String clientId;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    private String nombre;

    private String username;

    private String imageUrl;

}
