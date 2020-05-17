package com.poliplanner.controller;

import com.poliplanner.domain.dto.InscripcionDto;
import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.domain.model.Usuario;
import com.poliplanner.domain.pojo.Seccion;
import com.poliplanner.service.IInscripcionService;
import com.poliplanner.service.ISeccionService;
import com.poliplanner.service.IUsuarioService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class InscripcionController {
    private IInscripcionService inscripcionService;
    private ISeccionService seccionService;

    private IUsuarioService usuarioService;

    public InscripcionController(IInscripcionService inscripcionService, ISeccionService seccionService, IUsuarioService usuarioService) {
        this.inscripcionService = inscripcionService;
        this.seccionService = seccionService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Inscripcion> listInscripciones(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);
        return inscripcionService.findInscripciones(token.getSubject());
    }

    @GetMapping(path = "/last")
    public InscripcionDto lastInscripcion(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);

        Inscripcion inscripcion = inscripcionService.findLastInscripcion(token.getSubject());
        return new InscripcionDto(inscripcion, seccionService.findByUUID(inscripcion.getSecciones()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InscripcionDto saveInscripcionUser(KeycloakAuthenticationToken principal, @RequestBody List<UUID> secciones){
        AccessToken token = getPrincipalToken(principal);

        Usuario usuario = new Usuario();
        usuario.setClientId(token.getSubject());
        usuario.setEmail(token.getEmail());
        usuario.setImageUrl(token.getPicture());
        usuario.setNombre(token.getName());
        usuario.setUsername(token.getPreferredUsername());
        usuario = usuarioService.saveOrUpdate(usuario);

        Inscripcion inscripcion = new Inscripcion();

        List<Seccion> seccionList = seccionService.findByUUID(secciones);
        List<UUID> uuidList = seccionList.stream().map(seccion -> seccion.getUuid()).collect(Collectors.toList());

        inscripcion.setSecciones(uuidList);
        inscripcion.setUsuario(usuario);
        inscripcion = inscripcionService.save(inscripcion);

        return new InscripcionDto(inscripcion, seccionList);
    }

    public AccessToken getPrincipalToken(KeycloakAuthenticationToken principal){
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal.getPrincipal();
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) keycloakPrincipal.getKeycloakSecurityContext();
        return context.getToken();
    }
}
