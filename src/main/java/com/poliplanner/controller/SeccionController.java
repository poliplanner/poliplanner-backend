package com.poliplanner.controller;

import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Seccion;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public Iterable<Seccion> listSecciones(@RequestParam("carrera") Optional<List<String>> carrerasOptional,
                                           @RequestParam("horario") Optional<String> horarioUuidOptional,

                                           @RequestParam("uuid") Optional<List<UUID>>  seccionUuidOptional){
        if(seccionUuidOptional.isPresent()){
            List<UUID> seccionUuid = seccionUuidOptional.get();
            return repo.findAllByUuidIn(seccionUuid);
        } else if(carrerasOptional.isPresent() && horarioUuidOptional.isPresent()) {
            List<String> carreras = carrerasOptional.get();
            String horarioUuid = horarioUuidOptional.get();

            return repo.findByMateria_Carrera_CodigoInAndHorario_Uuid(carreras,UUID.fromString(horarioUuid));
        }
        return null;
    }


}