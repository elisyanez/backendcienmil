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

    
    // Autowired sirve para inyectar dependencias automaticamente
    // Se refiere a dependencias a los objetos que una clase necesita para funcionar
    // como por ejemplo un repositorio para acceder a datos
    // La anotacion @Autowired le dice a Spring que debe proporcionar automaticamente
    // una instancia del repositorio cuando se cree una instancia del servicio
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
