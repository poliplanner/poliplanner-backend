package com.poliplanner.service.data;

import com.poliplanner.domain.model.Inscripcion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InscripcionRepository extends CrudRepository<Inscripcion,Long> {
    Inscripcion findFirstByUsuario_ClientIdOrderByCreatedAtDesc(String clientId);
    List<Inscripcion> findByUsuario_ClientIdOrderByCreatedAtDesc(String clientId);
}
