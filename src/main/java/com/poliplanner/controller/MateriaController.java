package com.poliplanner.controller;

import com.poliplanner.data.CarreraRepository;
import com.poliplanner.data.MateriaRepository;
import com.poliplanner.domain.model.Materia;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/materias", produces = "application/json")
public class MateriaController {
    private MateriaRepository repo;

    public MateriaController(MateriaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Materia> listMaterias(@RequestParam("carrera") String carrera){
        Collection<String> carreras = List.of(carrera,"LCIK");
        return repo.findByCarreraCodigoIn(carreras);
    }
}
