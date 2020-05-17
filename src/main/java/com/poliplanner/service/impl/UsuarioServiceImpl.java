package com.poliplanner.service.impl;

import com.poliplanner.domain.model.Usuario;
import com.poliplanner.service.IUsuarioService;
import com.poliplanner.service.data.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private UsuarioRepository usuarioRepository;
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario saveOrUpdate(Usuario usuario) {
        return usuarioRepository.saveOrUpdate(usuario);
    }
}
