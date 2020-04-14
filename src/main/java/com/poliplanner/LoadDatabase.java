package com.poliplanner;

import com.poliplanner.config.AppProperties;
import com.poliplanner.data.*;
import com.poliplanner.domain.enums.AuthProvider;
import com.poliplanner.domain.enums.Dia;
import com.poliplanner.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

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

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner dataLoader() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                createDummyData();

                setupPrivileges();

            }
        };
    }
    private void createDummyData(){
        Horario h = horarioRepository.save(new Horario(null, UUID.randomUUID(),new Date(), "horario 1",null));
        Carrera c = carreraRepository.save(new Carrera(null,UUID.randomUUID(),"ca1","carrera1",10));
        Materia m = materiaRepository.save(new Materia(null,UUID.randomUUID(),c,"materia 1",null,null,5));
        Aula a = aulaRepository.save(new Aula(null,UUID.randomUUID(),"A51"));
        Clase c1 = claseRepository.save(new Clase(null,UUID.randomUUID(), Clase.Tipo.CLASE, Dia.JUEVES,"08:00","10:00",a));
        Clase c2 = claseRepository.save(new Clase(null,UUID.randomUUID(), Clase.Tipo.CLASE, Dia.LUNES,"08:00","10:00",a));
        Seccion s = seccionRepository.save(new Seccion(null, UUID.randomUUID(),"MQ",h,m,null,null, List.of(c1,c2)));
        inscripcionRepository.save(new Inscripcion(null, UUID.randomUUID(), List.of(s), null));
    }
    private void setupPrivileges(){
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        Usuario user = new Usuario();
        user.setNombre("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setAuthProvider(AuthProvider.local);
        user.setRoles(Arrays.asList(adminRole));
        usuarioRepository.save(user);
    }

    private Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> p = privilegeRepository.findByName(name);
        Privilege privilege = null;
        if(p.isPresent()){
            privilege = p.get();
        }else{
            privilege = new Privilege(null, name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Optional<Role> r = roleRepository.findByName(name);
        Role role = null;

        if (r.isPresent()) {
            role = r.get();
        }else{
            role = new Role(null,name,privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
