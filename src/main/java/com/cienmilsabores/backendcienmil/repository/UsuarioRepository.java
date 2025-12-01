package com.cienmilsabores.backendcienmil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.cienmilsabores.backendcienmil.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByCorreo(String correo);
	boolean existsByCorreo(String correo);
	boolean existsByRun(String run);
}
