package com.cienmilsabores.backendcienmil.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.cienmilsabores.backendcienmil.repository.UsuarioRepository;
import com.cienmilsabores.backendcienmil.model.Usuario;
import com.cienmilsabores.backendcienmil.dto.request.RegisterRequest;
import com.cienmilsabores.backendcienmil.exception.AuthenticationException;
import com.cienmilsabores.backendcienmil.exception.UsuarioExistenteException;

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

    // --- Methods used by AuthController ---
    public Usuario authenticate(String correo, String password) {
        Optional<Usuario> opt = usuarioRepository.findByCorreo(correo);
        if (opt.isEmpty()) throw new AuthenticationException("Usuario no encontrado");
        Usuario u = opt.get();
        if (u.getPassword() == null || !u.getPassword().equals(password)) {
            throw new AuthenticationException("Credenciales invalidas");
        }
        return u;
    }

    public Usuario registrarUsuario(RegisterRequest req) {
        if (usuarioRepository.existsByCorreo(req.getCorreo()) || usuarioRepository.existsByRun(req.getRun())) {
            throw new UsuarioExistenteException("El usuario ya existe");
        }
        Usuario u = new Usuario();
        u.setRun(req.getRun());
        u.setNombre(req.getNombre());
        u.setApellidos(req.getApellidos());
        u.setCorreo(req.getCorreo());
        u.setPassword(req.getPassword());
        // default role
        u.setRole("USER");
        // region/comuna/direccion left null for now
        return usuarioRepository.save(u);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
