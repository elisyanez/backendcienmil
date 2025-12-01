package com.cienmilsabores.backendcienmil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cienmilsabores.backendcienmil.dto.request.LoginRequest;
import com.cienmilsabores.backendcienmil.dto.request.RegisterRequest;
import com.cienmilsabores.backendcienmil.dto.response.LoginResponse;
import com.cienmilsabores.backendcienmil.dto.response.ErrorResponse;
import com.cienmilsabores.backendcienmil.dto.response.UserProfileResponse;
import com.cienmilsabores.backendcienmil.model.Usuario;
import com.cienmilsabores.backendcienmil.service.JwtService;
import com.cienmilsabores.backendcienmil.service.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

import com.cienmilsabores.backendcienmil.exception.AuthenticationException;
import com.cienmilsabores.backendcienmil.exception.UsuarioExistenteException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioServicio usuarioService;

    @Autowired
    private JwtService jwtService;

    // ✅ LOGIN - Ruta: POST /api/auth/login
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna token JWT")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            Usuario usuario = usuarioService.authenticate(loginRequest.getCorreo(), loginRequest.getPassword());
            
            // Generar token JWT
            String token = jwtService.generateToken(usuario);
            
            // Crear respuesta (usar codigo y nombre para region/comuna)
            LoginResponse response = new LoginResponse(
                token,
                usuario.getRun(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                usuario.getRole(),
                usuario.getRegion() != null ? usuario.getRegion().name() : null,
                usuario.getRegion() != null ? usuario.getRegion().getNombre() : null,
                usuario.getComuna() != null ? usuario.getComuna().name() : null,
                usuario.getComuna() != null ? usuario.getComuna().getNombre() : null,
                usuario.getDireccion()
            );

            return ResponseEntity.ok(response);
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Credenciales inválidas"));
        }
    }

    // ✅ REGISTER - Ruta: POST /api/auth/register  
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(registerRequest);
            
            // Generar token automáticamente después del registro
            String token = jwtService.generateToken(nuevoUsuario);
            
            LoginResponse response = new LoginResponse(
                token,
                nuevoUsuario.getRun(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellidos(),
                nuevoUsuario.getCorreo(),
                nuevoUsuario.getRole(),
                nuevoUsuario.getRegion() != null ? nuevoUsuario.getRegion().name() : null,
                nuevoUsuario.getRegion() != null ? nuevoUsuario.getRegion().getNombre() : null,
                nuevoUsuario.getComuna() != null ? nuevoUsuario.getComuna().name() : null,
                nuevoUsuario.getComuna() != null ? nuevoUsuario.getComuna().getNombre() : null,
                nuevoUsuario.getDireccion()
            );

            return ResponseEntity.status(201).body(response);
            
        } catch (UsuarioExistenteException e) {
            return ResponseEntity.status(400).body(new ErrorResponse("El usuario ya existe"));
        }
    }

    // ✅ VALIDATE TOKEN - Ruta: GET /api/auth/validate
    @Operation(summary = "Validar token", description = "Valida si el token JWT es válido")
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        try {
            String token = jwtService.extractToken(request);
            if (token != null && jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                Usuario usuario = usuarioService.findByCorreo(username);
                
                return ResponseEntity.ok(new UserProfileResponse(
                    usuario.getRun(),
                    usuario.getNombre(),
                    usuario.getApellidos(),
                    usuario.getCorreo(),
                    usuario.getRole(),
                    usuario.getRegion() != null ? usuario.getRegion().name() : null,
                    usuario.getRegion() != null ? usuario.getRegion().getNombre() : null,
                    usuario.getComuna() != null ? usuario.getComuna().name() : null,
                    usuario.getComuna() != null ? usuario.getComuna().getNombre() : null,
                    usuario.getDireccion()
                ));
            }
            return ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}