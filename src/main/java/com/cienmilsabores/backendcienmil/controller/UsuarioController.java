package com.cienmilsabores.backendcienmil.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    // Nuestra app utiliza getall para poder loggear el usuario y mostrar su perfil
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
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            log.info("Create usuario -> run={} correo={} rol={}", usuario.getRun(), usuario.getCorreo(),
                    usuario.getRole());
            Usuario saved = usuarioServicio.save(usuario);
            log.info("Usuario guardado OK -> run={} id={}", saved.getRun(), saved.getRun());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario: " + e.getMessage());
        }
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

    @GetMapping("/me")
    @Operation(summary = "Obtener perfil del usuario autenticado", description = "Devuelve los datos del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Perfil del usuario obtenido exitosamente")
    public ResponseEntity<Usuario> getCurrentUserProfile(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("[getCurrentUserProfile] Usuario no autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Spring Security nos da el 'principal', que es el correo (username)
            String userEmail = authentication.getName();
            log.info("[getCurrentUserProfile] Obteniendo perfil para usuario: {}", userEmail);
            
            // Buscar al usuario en el repositorio
            Usuario usuario = usuarioServicio.findByCorreo(userEmail);
            log.info("[getCurrentUserProfile] Perfil obtenido exitosamente para: {}", userEmail);
            
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("[getCurrentUserProfile] Error al obtener perfil: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

