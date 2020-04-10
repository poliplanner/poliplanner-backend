package com.poliplanner.data;

import com.poliplanner.domain.model.Seccion;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface SeccionRepository extends CrudRepository<Seccion, Long> {
    List<Seccion> findByMateria_Carrera_CodigoInAndHorario_Uuid(Collection<String> carreras, Long horario);
}