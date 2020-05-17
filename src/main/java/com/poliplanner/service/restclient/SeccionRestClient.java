package com.poliplanner.service.restclient;

import com.poliplanner.domain.pojo.Seccion;
import com.poliplanner.utils.HorarioProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
public class SeccionRestClient {
    @Autowired
    private RestTemplate rest;
    @Autowired
    private HorarioProps horarioProps;

    public List<Seccion> getSeccionesByUuid(List<UUID> uuidList){
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(horarioProps.getSeccionesUrl())
                .queryParam("uuid", uuidList)
                .build();

        ResponseEntity<Seccion[]> responseEntity = rest.getForEntity(builder.toUriString(), Seccion[].class);
        return List.of(responseEntity.getBody());
    }

}
