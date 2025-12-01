package com.cienmilsabores.backendcienmil.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String run;
    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
}
