package com.cienmilsabores.backendcienmil.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(unique = true)
    private String run;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellidos;

    @Column(unique = true)
    private String correo;

    @Column(length = 50)
    private String role;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Comuna comuna;

    @Column(length = 100)
    private String direccion;
}
