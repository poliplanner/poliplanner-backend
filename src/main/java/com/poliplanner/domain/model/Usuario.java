package com.poliplanner.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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
