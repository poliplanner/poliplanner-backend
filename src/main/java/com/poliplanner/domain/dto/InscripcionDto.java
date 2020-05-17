package com.poliplanner.domain.dto;

import com.poliplanner.domain.model.Inscripcion;
import com.poliplanner.domain.model.Usuario;
import com.poliplanner.domain.pojo.Seccion;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InscripcionDto {
    private UUID uuid;
    private Usuario usuario;
    private List<Seccion> secciones;

    public InscripcionDto(Inscripcion inscripcion, List<Seccion> secciones){
        this.uuid = inscripcion.getUuid();
        this.usuario = inscripcion.getUsuario();
        this.secciones = secciones;
    }

}
