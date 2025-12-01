package com.cienmilsabores.backendcienmil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    private String codigo;

    @Column(length = 100)
    private String nombre;
    @Column(length = 500)
    private String descripcion;
    @Column(length = 50)
    private String categoria;
    @Column(length = 200)
    private String imagenUrl;
    @Column
    private double precio;
    @Column
    private boolean visible;
    @Column
    private int stock;


}
