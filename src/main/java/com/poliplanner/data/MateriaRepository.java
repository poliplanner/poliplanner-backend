package com.poliplanner.data;

import com.poliplanner.domain.model.Carrera;
import com.poliplanner.domain.model.Materia;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface MateriaRepository extends CrudRepository<Materia, Long> {
    List<Materia> findByCarreraCodigoIn(Collection<String> carreras);
    List<Materia> findByCarrera(Carrera carrera);
}
