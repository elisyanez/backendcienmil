package com.cienmilsabores.backendcienmil.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import com.cienmilsabores.backendcienmil.model.Usuario;
import com.cienmilsabores.backendcienmil.service.UsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    public List<Usuario> getAll() {
        return usuarioServicio.listAll();
    }

    @Operation(summary = "Obtener usuario por RUN", description = "Devuelve un usuario especifico segun su RUN")
    @GetMapping("/{run}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> getByRun(@PathVariable String run) {
        Optional<Usuario> usuario = usuarioServicio.getByRun(run);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario con la informacion proporcionada")
    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida")
    })
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario saved = usuarioServicio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza la informacion de un usuario existente segun su RUN")
    @PutMapping("/{run}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> update(@PathVariable String run, @RequestBody Usuario usuario) {
        Optional<Usuario> existing = usuarioServicio.getByRun(run);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuario.setRun(run);
        Usuario updated = usuarioServicio.save(usuario);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario especifico segun su RUN")
    @DeleteMapping("/{run}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable String run) {
        Optional<Usuario> existing = usuarioServicio.getByRun(run);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioServicio.deleteByRun(run);
        return ResponseEntity.noContent().build();
    }
}
