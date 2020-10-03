package com.poliplanner.service.impl;

import com.poliplanner.data.SeccionRepository;
import com.poliplanner.domain.model.Seccion;
import com.poliplanner.service.ISeccionService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SeccionService implements ISeccionService {
    private SeccionRepository seccionRepository;

    public SeccionService(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    @Override
    public List<Seccion> findByUUID(Collection<UUID> secciones) {
        return seccionRepository.findAllByUuidIn(secciones);
    }
}
