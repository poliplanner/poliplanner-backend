package com.poliplanner.controller;

import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.domain.model.Usuario;
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

@RestController
@RequestMapping(path = "/users/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class InscripcionController {
    private IInscripcionService inscripcionService;
    private ISeccionService seccionService;

    private IUsuarioService usuarioService;

    @GetMapping
    public List<Inscripcion> listInscripciones(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);
        return inscripcionService.findInscripciones(token.getSubject());
    }

    @GetMapping(path = "/last")
    public Inscripcion lastInscripcion(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);
        return inscripcionService.findLastInscripcion(token.getSubject());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Inscripcion saveInscripcionUser(KeycloakAuthenticationToken principal, @RequestBody List<UUID> secciones){
        AccessToken token = getPrincipalToken(principal);

        Usuario usuario = new Usuario();
        usuario.setClientId(token.getSubject());
        usuario.setEmail(token.getEmail());
        usuario.setImageUrl(token.getPicture());
        usuario.setNombre(token.getName());
        usuario.setUsername(token.getPreferredUsername());
        usuario = usuarioService.saveOrUpdate(usuario);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setSecciones(seccionService.findByUUID(secciones));
        inscripcion.setUsuario(usuario);

        return inscripcionService.save(inscripcion);
    }

    public AccessToken getPrincipalToken(KeycloakAuthenticationToken principal){
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal.getPrincipal();
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) keycloakPrincipal.getKeycloakSecurityContext();
        return context.getToken();
    }
}
