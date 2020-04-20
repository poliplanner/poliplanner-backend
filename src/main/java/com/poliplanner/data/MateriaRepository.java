package com.poliplanner.data;

import com.poliplanner.domain.model.Carrera;
import com.poliplanner.domain.model.Materia;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface MateriaRepository extends CrudRepository<Materia, Long> {
    List<Materia> findByCarreraCodigoIn(Collection<String> carreras);
    List<Materia> findByCarreraOrderByNombre(Carrera carrera);
}
