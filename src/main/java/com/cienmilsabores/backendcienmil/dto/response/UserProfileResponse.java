package com.cienmilsabores.backendcienmil.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
	private String run;
	private String nombre;
	private String apellidos;
	private String correo;
	private String role;
	private String regionCodigo;
	private String regionNombre;
	private String comunaCodigo;
	private String comunaNombre;
	private String direccion;
}
