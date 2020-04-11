package com.poliplanner.controller;

import com.poliplanner.data.HorarioRepository;
import com.poliplanner.domain.model.Horario;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/horarios", produces = "application/json")
@CrossOrigin("*")
public class HorarioController {
    private HorarioRepository repo;

    public HorarioController(HorarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Horario> listHorario(){
        return repo.findAll();
    }
}

