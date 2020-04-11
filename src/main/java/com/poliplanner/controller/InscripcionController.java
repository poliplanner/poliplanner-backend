package com.poliplanner.controller;

import com.poliplanner.data.InscripcionRepository;
import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.domain.model.Seccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class InscripcionController {
    private InscripcionRepository inscripcionRepository;
    private SeccionRepository seccionRepository;

    public InscripcionController(InscripcionRepository inscripcionRepository, SeccionRepository seccionRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.seccionRepository = seccionRepository;
    }

    @GetMapping
    public Inscripcion lastInscripcion(){
        return inscripcionRepository.findFirstByOrderByCreatedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inscripcion saveInscripcionUser(@RequestBody List<UUID> secciones){

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setSecciones(seccionRepository.findAllByUuidIn(secciones));

        return inscripcionRepository.save(inscripcion);
    }
}
