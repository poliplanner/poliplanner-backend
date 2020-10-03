package com.poliplanner.data;

import com.poliplanner.domain.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    default Usuario saveOrUpdate(Usuario usuario){
        Usuario u = findById(usuario.getClientId()).orElse(save(usuario));
        return u;
    }
}
