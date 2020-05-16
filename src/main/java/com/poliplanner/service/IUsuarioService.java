package com.poliplanner.service;

import com.poliplanner.domain.model.Usuario;

public interface IUsuarioService {
    Usuario saveOrUpdate(Usuario usuario);
}
