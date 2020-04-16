package com.poliplanner.controller;

import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Seccion;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public Iterable<Seccion> listSecciones(@RequestParam Map<String, String> params){
        String carrera = params.get("carrera");
        String horario = params.get("horario");

        Collection<String> carreras = List.of(carrera,"LCIK");

        return repo.findByMateria_Carrera_CodigoInAndHorario_Uuid(carreras,UUID.fromString(horario));
    }
}
