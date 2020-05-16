package com.poliplanner.service;

import com.poliplanner.domain.model.Inscripcion;

import java.util.List;

public interface IInscripcionService {
    Inscripcion findLastInscripcion(String clientId);
    List<Inscripcion> findInscripciones(String clientId);

    Inscripcion save(Inscripcion inscripcion);
}
