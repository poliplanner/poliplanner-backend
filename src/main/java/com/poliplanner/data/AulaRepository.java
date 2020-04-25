package com.poliplanner.data;

import com.poliplanner.domain.model.Aula;
import org.springframework.data.repository.CrudRepository;

import java.util.*;
import java.util.stream.Collectors;

public interface AulaRepository extends CrudRepository<Aula, Long> {
    List<Aula> findByCodigoIn(Collection<String> codigos);

    default List<Aula> saveFindByCodigo(Collection<String> codigos){
        List<Aula> aulas = findByCodigoIn(codigos);

        if(aulas.size() != codigos.size()){
            Map<String, Aula> map = new HashMap<>();
            for(Aula aula: aulas){
                map.put(aula.getCodigo(),aula);
            }

            Iterable<Aula> nuevasAulas = codigos.stream()
                    .filter(codigo -> !map.containsKey(codigo))
                    .map(codigo -> {
                        Aula a = new Aula();
                        a.setCodigo(codigo);
                        return a;
                    }).collect(Collectors.toList());

            nuevasAulas = saveAll(nuevasAulas);
            nuevasAulas.forEach(aulas::add);
        }

        return aulas;
    }

}
