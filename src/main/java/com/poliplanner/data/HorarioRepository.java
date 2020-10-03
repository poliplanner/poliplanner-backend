package com.poliplanner.data;

import com.poliplanner.domain.model.Horario;
import org.springframework.data.repository.CrudRepository;

public interface HorarioRepository extends CrudRepository<Horario,Long> {
}
