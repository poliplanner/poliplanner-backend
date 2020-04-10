package com.poliplanner.data;

import com.poliplanner.domain.model.Inscripcion;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InscripcionRepository extends CrudRepository<Inscripcion,Long> {
    Inscripcion findFirstByOrderByCreatedAtDesc();
}
