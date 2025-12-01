package com.cienmilsabores.backendcienmil.controller;

import com.cienmilsabores.backendcienmil.model.*;
import com.cienmilsabores.backendcienmil.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @Operation(summary = "Crear nuevo pedido")
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request) {
        try {
            Pedido pedido = pedidoService.crearPedido(request.getUsuarioRun(), request.getItems());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Obtener pedidos del usuario")
    @GetMapping("/usuario/{usuarioRun}")
    public ResponseEntity<List<Pedido>> obtenerPedidosUsuario(@PathVariable String usuarioRun) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioRun);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Obtener todos los pedidos (admin)")
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }
    
    @Operation(summary = "Actualizar estado del pedido")
    @PutMapping("/{pedidoId}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long pedidoId,
            @RequestBody ActualizarEstadoRequest request) {
        try {
            Pedido pedido = pedidoService.actualizarEstado(pedidoId, request.getNuevoEstado());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DTOs para requests
    public static class CrearPedidoRequest {
        private String usuarioRun;
        private List<ItemCarrito> items;
        
        // getters y setters
        public String getUsuarioRun() { return usuarioRun; }
        public void setUsuarioRun(String usuarioRun) { this.usuarioRun = usuarioRun; }
        
        public List<ItemCarrito> getItems() { return items; }
        public void setItems(List<ItemCarrito> items) { this.items = items; }
    }
    
    public static class ActualizarEstadoRequest {
        private EstadoPedido nuevoEstado;
        
        // getters y setters
        public EstadoPedido getNuevoEstado() { return nuevoEstado; }
        public void setNuevoEstado(EstadoPedido nuevoEstado) { this.nuevoEstado = nuevoEstado; }
    }
}