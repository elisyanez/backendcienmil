package com.cienmilsabores.backendcienmil.model;

public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    EN_PREPARACION("En preparación"),
    LISTO_PARA_ENVIO("Listo para envío"),
    EN_CAMINO("En camino"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");
    
    private final String descripcion;
    
    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
