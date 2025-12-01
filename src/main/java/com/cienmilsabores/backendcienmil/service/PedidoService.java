package com.cienmilsabores.backendcienmil.service;

import com.cienmilsabores.backendcienmil.model.*;
import com.cienmilsabores.backendcienmil.repository.PedidoRepository;
import com.cienmilsabores.backendcienmil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public Pedido crearPedido(String usuarioRun, List<ItemCarrito> itemsCarrito) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(usuarioRun)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Crear pedido
        Pedido pedido = new Pedido(usuario);
        
        // Generar número único de pedido
        String numeroPedido = generarNumeroPedido();
        pedido.setNumeroPedido(numeroPedido);
        
        // Convertir items del carrito a detalles de pedido
        for (ItemCarrito itemCarrito : itemsCarrito) {
            DetallePedido detalle = new DetallePedido(
                itemCarrito.getProducto(),
                itemCarrito.getCantidad(),
                itemCarrito.getMensajePersonalizado()
            );
            pedido.agregarItem(detalle);
        }
        
        // Calcular totales
        pedido.calcularTotales();
        
        // Guardar pedido
        return pedidoRepository.save(pedido);
    }
    
    private String generarNumeroPedido() {
        int year = LocalDateTime.now().getYear();
        Long count = pedidoRepository.countPedidosByYear(year);
        return String.format("PED-%d-%04d", year, count + 1);
    }
    
    public List<Pedido> obtenerPedidosPorUsuario(String usuarioRun) {
        Usuario usuario = usuarioRepository.findById(usuarioRun)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return pedidoRepository.findByUsuarioOrderByFechaDesc(usuario);
    }
    
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAllByOrderByFechaDesc();
    }
    
    @Transactional
    public Pedido actualizarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
    
    public Optional<Pedido> obtenerPedidoPorNumero(String numeroPedido) {
        return pedidoRepository.findByNumeroPedido(numeroPedido);
    }
}