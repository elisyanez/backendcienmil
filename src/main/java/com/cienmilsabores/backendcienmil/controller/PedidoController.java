package com.cienmilsabores.backendcienmil.controller;

import com.cienmilsabores.backendcienmil.model.*;
import com.cienmilsabores.backendcienmil.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PedidoController {
    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoService pedidoService;
    
    @Operation(summary = "Crear nuevo pedido")
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request) {
        logger.info("[crearPedido] request usuarioRun={} itemsCount={} ", request.getUsuarioRun(), request.getItems() == null ? 0 : request.getItems().size());
        try {
            Pedido pedido = pedidoService.crearPedido(request.getUsuarioRun(), request.getItems());
            logger.info("[crearPedido] pedido creado id={} numeroPedido={}", pedido.getId(), pedido.getNumeroPedido());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            logger.error("[crearPedido] error creating pedido for usuarioRun={}: {}", request.getUsuarioRun(), e.toString(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Obtener pedidos del usuario")
    @GetMapping("/usuario/{usuarioRun}")
    public ResponseEntity<List<Pedido>> obtenerPedidosUsuario(@PathVariable String usuarioRun) {
        logger.info("[obtenerPedidosUsuario] usuarioRun={}", usuarioRun);
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioRun);
            logger.info("[obtenerPedidosUsuario] found {} pedidos for usuarioRun={}", pedidos == null ? 0 : pedidos.size(), usuarioRun);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            logger.error("[obtenerPedidosUsuario] error fetching pedidos for usuarioRun={}: {}", usuarioRun, e.toString(), e);
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Obtener todos los pedidos (admin)")
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosPedidos() {
        logger.info("[obtenerTodosPedidos] fetching all pedidos");
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        logger.info("[obtenerTodosPedidos] totalPedidos={}", pedidos == null ? 0 : pedidos.size());
        return ResponseEntity.ok(pedidos);
    }
    
    @Operation(summary = "Actualizar estado del pedido")
    @PutMapping("/{pedidoId}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long pedidoId,
            @RequestBody ActualizarEstadoRequest request) {
        logger.info("[actualizarEstado] pedidoId={} nuevoEstado={}", pedidoId, request.getNuevoEstado());
        try {
            Pedido pedido = pedidoService.actualizarEstado(pedidoId, request.getNuevoEstado());
            logger.info("[actualizarEstado] pedido actualizado id={} estado={}", pedido.getId(), pedido.getEstado());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            logger.error("[actualizarEstado] error updating estado for pedidoId={}: {}", pedidoId, e.toString(), e);
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