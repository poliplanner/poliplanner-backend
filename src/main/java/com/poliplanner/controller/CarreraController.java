package com.poliplanner.controller;

import com.poliplanner.data.CarreraRepository;
import com.poliplanner.domain.model.Carrera;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path="/carreras", produces="application/json")
@CrossOrigin(origins="*")
public class CarreraController {
    private CarreraRepository repo;

    public CarreraController(CarreraRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Carrera> listCarreras(){
        return repo.findAll();
    }
}
