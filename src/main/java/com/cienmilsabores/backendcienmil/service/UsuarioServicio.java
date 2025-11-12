package com.cienmilsabores.backendcienmil.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.cienmilsabores.backendcienmil.repository.UsuarioRepository;
import com.cienmilsabores.backendcienmil.model.Usuario;

@Service
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServicio(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getByRun(String run) {
        return usuarioRepository.findById(run);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteByRun(String run) {
        usuarioRepository.deleteById(run);
    }
}
