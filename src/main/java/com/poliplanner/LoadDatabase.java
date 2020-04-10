package com.poliplanner;

import com.poliplanner.data.*;
import com.poliplanner.domain.enums.Dia;
import com.poliplanner.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Configuration
public class LoadDatabase {
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private ClaseRepository claseRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Bean
    public CommandLineRunner dataLoader() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Horario h = horarioRepository.save(new Horario(null, UUID.randomUUID(),new Date(), "horario 1",null));
                Carrera c = carreraRepository.save(new Carrera(null,UUID.randomUUID(),"ca1","carrera1",10));
                Materia m = materiaRepository.save(new Materia(null,UUID.randomUUID(),c,"materia 1",null,null,5));
                Aula a = aulaRepository.save(new Aula(null,UUID.randomUUID(),"A51"));
                Clase c1 = claseRepository.save(new Clase(null,UUID.randomUUID(), Clase.Tipo.CLASE, Dia.JUEVES,"08:00","10:00",a));
                Clase c2 = claseRepository.save(new Clase(null,UUID.randomUUID(), Clase.Tipo.CLASE, Dia.LUNES,"08:00","10:00",a));
                Seccion s = seccionRepository.save(new Seccion(null, UUID.randomUUID(),"MQ",h,m,null,null, List.of(c1,c2)));

            }
        };
    }
}
