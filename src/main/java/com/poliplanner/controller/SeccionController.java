package com.poliplanner.controller;

import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Seccion;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/fpuna/secciones", produces="application/json")
@CrossOrigin(origins="*")
public class SeccionController {
    private SeccionRepository repo;

    public SeccionController(SeccionRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Seccion> listSecciones(@RequestParam("carrera") List<String> carreras,
                                           @RequestParam("horario") String horarioUuid){
        return repo.findByMateria_Carrera_CodigoInAndHorario_Uuid(carreras,UUID.fromString(horarioUuid));
    }
}
