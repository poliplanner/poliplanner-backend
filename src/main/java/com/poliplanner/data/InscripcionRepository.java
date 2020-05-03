package com.poliplanner.data;

import com.poliplanner.domain.model.Inscripcion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InscripcionRepository extends CrudRepository<Inscripcion,Long> {
    Inscripcion findFirstByUsuario_ClientIdOrderByCreatedAtDesc(String clientId);
    List<Inscripcion> findByUsuario_ClientIdOrderByCreatedAtDesc(String clientId);
}
