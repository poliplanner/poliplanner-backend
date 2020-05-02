package com.poliplanner.controller;

import com.poliplanner.data.MateriaRepository;
import com.poliplanner.domain.model.Materia;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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
    public Iterable<Materia> listMaterias(KeycloakAuthenticationToken principal, @RequestParam("carrera") List<String> carreras){
        /*KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal.getPrincipal();
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) keycloakPrincipal.getKeycloakSecurityContext();
        System.out.println(context.getToken().getName());*/

        return repo.findByCarreraCodigoIn(carreras);
    }
}
