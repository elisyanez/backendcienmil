package com.cienmilsabores.backendcienmil.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private String run;
	private String nombre;
	private String apellidos;
	private String correo;
	private String role;
	// Expose region and comuna as codes and display names (strings) to avoid enum serialization issues on clients
	private String regionCodigo;
	private String regionNombre;
	private String comunaCodigo;
	private String comunaNombre;
	private String direccion;
}
