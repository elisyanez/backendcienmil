package com.cienmilsabores.backendcienmil.repository;

import com.cienmilsabores.backendcienmil.model.Pedido;
import com.cienmilsabores.backendcienmil.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Encontrar pedidos por usuario
    List<Pedido> findByUsuarioOrderByFechaDesc(Usuario usuario);
    
    // Encontrar pedido por número único
    Optional<Pedido> findByNumeroPedido(String numeroPedido);
    
    // Encontrar todos los pedidos ordenados por fecha (para admin)
    List<Pedido> findAllByOrderByFechaDesc();
    
    // Contar pedidos para generar número único
    @Query("SELECT COUNT(p) FROM Pedido p WHERE YEAR(p.fecha) = :year")
    Long countPedidosByYear(@Param("year") int year);
}