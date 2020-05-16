package com.poliplanner.service;

import com.poliplanner.domain.model.Seccion;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ISeccionService {
    List<Seccion> findByUUID(Collection<UUID> secciones);
}
