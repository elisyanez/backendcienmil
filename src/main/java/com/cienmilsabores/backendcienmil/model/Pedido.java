package com.cienmilsabores.backendcienmil.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroPedido; // PED-2024-0001
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> items = new ArrayList<>();
    
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal iva; // 19%

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;
    
    // Datos de envío (copiados del usuario al momento de la compra)
    @Column(nullable = false)
    private String direccionEnvio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Region regionEnvio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Comuna comunaEnvio;
    
    // Constructores
    public Pedido() {
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
        this.subtotal = BigDecimal.ZERO;
        this.iva = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }
    
    public Pedido(Usuario usuario) {
        this();
        this.usuario = usuario;
        // Copiar datos de envío del usuario
        this.direccionEnvio = usuario.getDireccion();
        this.regionEnvio = usuario.getRegion();
        this.comunaEnvio = usuario.getComuna();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }
    
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public List<DetallePedido> getItems() { return items; }
    public void setItems(List<DetallePedido> items) { this.items = items; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    
    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    
    public Region getRegionEnvio() { return regionEnvio; }
    public void setRegionEnvio(Region regionEnvio) { this.regionEnvio = regionEnvio; }
    
    public Comuna getComunaEnvio() { return comunaEnvio; }
    public void setComunaEnvio(Comuna comunaEnvio) { this.comunaEnvio = comunaEnvio; }
    
    // Método helper para agregar items
    public void agregarItem(DetallePedido item) {
        item.setPedido(this);
        this.items.add(item);
    }
    
    // Método para calcular totales
    public void calcularTotales() {
        this.subtotal = items.stream()
            .map(DetallePedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.iva = this.subtotal.multiply(new BigDecimal("0.19"));
        this.total = this.subtotal.add(this.iva);
    }
}