package com.cienmilsabores.backendcienmil.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import com.cienmilsabores.backendcienmil.model.Usuario;
import com.cienmilsabores.backendcienmil.service.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioServicio.listAll();
    }

    @GetMapping("/{run}")
    public ResponseEntity<Usuario> getByRun(@PathVariable String run) {
        Optional<Usuario> usuario = usuarioServicio.getByRun(run);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario saved = usuarioServicio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{run}")
    public ResponseEntity<Usuario> update(@PathVariable String run, @RequestBody Usuario usuario) {
        Optional<Usuario> existing = usuarioServicio.getByRun(run);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // Ensure the run (ID) in path and body match; set path value to body to be safe
        usuario.setRun(run);
        Usuario updated = usuarioServicio.save(usuario);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<Void> delete(@PathVariable String run) {
        Optional<Usuario> existing = usuarioServicio.getByRun(run);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioServicio.deleteByRun(run);
        return ResponseEntity.noContent().build();
    }
}
