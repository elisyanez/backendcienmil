package com.cienmilsabores.backendcienmil.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    // Expone region y comuna como códigos y nombres para evitar problemas de serialización de enums en los clientes
	private String regionCodigo;
	private String regionNombre;
	private String comunaCodigo;
	private String comunaNombre;
	private String direccion;
}
