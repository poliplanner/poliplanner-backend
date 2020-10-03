package com.poliplanner.controller;

import com.poliplanner.data.MateriaRepository;
import com.poliplanner.domain.model.Materia;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/fpuna/materias", produces = "application/json")
public class MateriaController {
    private MateriaRepository repo;

    public MateriaController(MateriaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Materia> listMaterias(@RequestParam("carrera") List<String> carreras){
        return repo.findByCarreraCodigoIn(carreras);
    }
}
