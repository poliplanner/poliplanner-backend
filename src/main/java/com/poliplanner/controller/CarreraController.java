package com.poliplanner.controller;

import com.poliplanner.data.CarreraRepository;
import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Carrera;
import com.poliplanner.domain.model.Seccion;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
