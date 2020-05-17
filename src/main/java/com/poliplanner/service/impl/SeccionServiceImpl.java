package com.poliplanner.service.impl;

import com.poliplanner.domain.pojo.Seccion;
import com.poliplanner.service.ISeccionService;
import com.poliplanner.service.restclient.SeccionRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class SeccionServiceImpl implements ISeccionService {
    @Autowired
    SeccionRestClient restClient;

    @Override
    public List<Seccion> findByUUID(List<UUID> secciones) {
        return restClient.getSeccionesByUuid(secciones);
    }
}
