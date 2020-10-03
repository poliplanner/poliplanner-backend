package com.poliplanner.service.impl;

import com.poliplanner.data.InscripcionRepository;
import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.service.IInscripcionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionServiceImpl implements IInscripcionService {
    private InscripcionRepository inscripcionRepository;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public Inscripcion findLastInscripcion(String clientId) {
        return inscripcionRepository.findFirstByUsuario_ClientIdOrderByCreatedAtDesc(clientId);
    }

    @Override
    public List<Inscripcion> findInscripciones(String clientId) {
        return inscripcionRepository.findByUsuario_ClientIdOrderByCreatedAtDesc(clientId);
    }

    @Override
    public Inscripcion save(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }
}
