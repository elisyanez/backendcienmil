package com.cienmilsabores.backendcienmil.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cienmilsabores.backendcienmil.model.Region;
import com.cienmilsabores.backendcienmil.model.Comuna;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
	private String run;
	private String nombre;
	private String apellidos;
	private String correo;
	private String role;
	private Region region;
	private Comuna comuna;
	private String direccion;
}
