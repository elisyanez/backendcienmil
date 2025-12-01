package com.cienmilsabores.backendcienmil.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cienmilsabores.backendcienmil.model.Region;
import com.cienmilsabores.backendcienmil.model.Comuna;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/util")
@Tag(name = "Utilidades", description = "Regiones y comunas disponibles")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UtilController {

    @Operation(summary = "Listar regiones", description = "Devuelve el catalogo de regiones")
    @GetMapping("/regiones")
    public List<RegionDTO> getRegiones() {
        return Arrays.stream(Region.values())
                .map(region -> new RegionDTO(region.name(), region.getNombre()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Listar comunas", description = "Devuelve el catalogo de comunas con su region")
    @GetMapping("/comunas")
    public List<ComunaDTO> getComunas() {
        return Arrays.stream(Comuna.values())
                .map(comuna -> new ComunaDTO(
                        comuna.name(),
                        comuna.getNombre(),
                        comuna.getRegion().name(),
                        comuna.getRegion().getNombre()
                ))
                .collect(Collectors.toList());
    }

    // DTO para Region
    public static class RegionDTO {
        public String codigo;
        public String nombre;

        public RegionDTO(String codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }
    }

    // DTO para Comuna
    public static class ComunaDTO {
        public String codigo;
        public String nombre;
        public String regionCodigo;
        public String regionNombre;

        public ComunaDTO(String codigo, String nombre, String regionCodigo, String regionNombre) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.regionCodigo = regionCodigo;
            this.regionNombre = regionNombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getRegionCodigo() {
            return regionCodigo;
        }

        public String getRegionNombre() {
            return regionNombre;
        }
    }
}
