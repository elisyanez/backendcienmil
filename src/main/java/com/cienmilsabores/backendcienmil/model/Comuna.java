package com.cienmilsabores.backendcienmil.model;

public enum Comuna {
    // Región Metropolitana
    SANTIAGO("Santiago", Region.REGION_METROPOLITANA),
    PROVIDENCIA("Providencia", Region.REGION_METROPOLITANA),
    LAS_CONDES("Las Condes", Region.REGION_METROPOLITANA),
    NUÑOA("Ñuñoa", Region.REGION_METROPOLITANA),
    MAIPU("Maipú", Region.REGION_METROPOLITANA),
    LA_FLORIDA("La Florida", Region.REGION_METROPOLITANA),
    SAN_BERNARDO("San Bernardo", Region.REGION_METROPOLITANA),
    PUDAHUEL("Pudahuel", Region.REGION_METROPOLITANA),
    QUILICURA("Quilicura", Region.REGION_METROPOLITANA),
    CERRILLOS("Cerrillos", Region.REGION_METROPOLITANA),
    CURACAVI("Curacaví", Region.REGION_METROPOLITANA),
    
    // Valparaíso
    VINA_DEL_MAR("Viña del Mar", Region.VALPARAISO),
    VALPARAISO("Valparaíso", Region.VALPARAISO),
    QUILPUE("Quilpué", Region.VALPARAISO),
    VILLA_ALEMANA("Villa Alemana", Region.VALPARAISO),
    CONCON("Concón", Region.VALPARAISO),

    // Biobío
    CONCEPCION("Concepción", Region.BIOBIO),
    TALCAHUANO("Talcahuano", Region.BIOBIO),
    CHIGUAYANTE("Chiguayante", Region.BIOBIO),
    SAN_PEDRO("San Pedro de la Paz", Region.BIOBIO);
    
    private final String nombre;
    private final Region region;
    
    Comuna(String nombre, Region region) {
        this.nombre = nombre;
        this.region = region;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Region getRegion() {
        return region;
    }
}
