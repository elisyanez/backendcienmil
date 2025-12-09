package com.cienmilsabores.backendcienmil.model;

public enum Region {
    REGION_METROPOLITANA("Región Metropolitana"),
    VALPARAISO("Región de Valparaíso"),
    BIOBIO("Región del Biobío"),
    MAULE("Región del Maule"),
    ARAUCANIA("Región de La Araucanía"),
    LOS_LAGOS("Región de Los Lagos"),
    COQUIMBO("Región de Coquimbo"),
    OHIGGINS("Región del Libertador General Bernardo O'Higgins"),
    ANTOFAGASTA("Región de Antofagasta"),
    ATACAMA("Región de Atacama"),
    MAGALLANES("Región de Magallanes y de la Antártica Chilena"),
    AYSEN("Región Aysén del General Carlos Ibáñez del Campo"),
    TARAPACA("Región de Tarapacá"),
    NUBLE("Región de Ñuble"),
    ARICA("Región de Arica y Parinacota"),
    LOS_RIOS("Región de Los Ríos");
    
    private final String nombre;
    
    Region(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
