package com.poliplanner.controller;

import com.poliplanner.data.InscripcionRepository;
import com.poliplanner.data.SeccionRepository;
import com.poliplanner.data.UsuarioRepository;
import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.domain.model.Seccion;
import com.poliplanner.domain.model.Usuario;
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
    private InscripcionRepository inscripcionRepository;
    private SeccionRepository seccionRepository;
    private UsuarioRepository usuarioRepository;

    public InscripcionController(InscripcionRepository inscripcionRepository, SeccionRepository seccionRepository, UsuarioRepository usuarioRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.seccionRepository = seccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Inscripcion> listInscripciones(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);
        return inscripcionRepository.findByUsuario_ClientIdOrderByCreatedAtDesc(token.getSubject());
    }

    @GetMapping(path = "/last")
    public Inscripcion lastInscripcion(KeycloakAuthenticationToken principal){
        AccessToken token = getPrincipalToken(principal);
        return inscripcionRepository.findFirstByUsuario_ClientIdOrderByCreatedAtDesc(token.getSubject());
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
        usuario = usuarioRepository.saveOrUpdate(usuario);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setSecciones(seccionRepository.findAllByUuidIn(secciones));
        inscripcion.setUsuario(usuario);

        return inscripcionRepository.save(inscripcion);
    }

    public AccessToken getPrincipalToken(KeycloakAuthenticationToken principal){
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) principal.getPrincipal();
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) keycloakPrincipal.getKeycloakSecurityContext();
        return context.getToken();
    }
}
