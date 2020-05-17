package com.poliplanner.service;

import com.poliplanner.domain.pojo.Seccion;

import java.util.List;
import java.util.UUID;

public interface ISeccionService {
    List<Seccion> findByUUID(List<UUID> secciones);
}
