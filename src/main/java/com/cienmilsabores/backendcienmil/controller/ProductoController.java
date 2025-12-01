package com.cienmilsabores.backendcienmil.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import com.cienmilsabores.backendcienmil.model.Producto;
import com.cienmilsabores.backendcienmil.service.ProductoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductoController {

	private final ProductoServicio productoServicio;

	public ProductoController(ProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}

	@Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos")
	@GetMapping
	@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
	public List<Producto> getAll() {
		return productoServicio.listAll();
	}

	@Operation(summary = "Obtener producto por codigo", description = "Devuelve un producto segun su codigo")
	@GetMapping("/{codigo}")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente"),
		@ApiResponse(responseCode = "404", description = "Producto no encontrado")
	})
	public ResponseEntity<Producto> getByCodigo(@PathVariable String codigo) {
		Optional<Producto> p = productoServicio.findByCodigo(codigo);
		return p.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Crear producto", description = "Crea un nuevo producto")
	@PostMapping
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
		@ApiResponse(responseCode = "400", description = "Solicitud invalida")
	})
	public ResponseEntity<?> create(@RequestBody Producto producto) {
		try {
			Producto saved = productoServicio.createProducto(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
	@PutMapping("/{codigo}")
	public ResponseEntity<?> update(@PathVariable String codigo, @RequestBody Producto producto) {
		try {
			Producto updated = productoServicio.updateProducto(codigo, producto);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@Operation(summary = "Eliminar producto", description = "Elimina un producto por codigo")
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable String codigo) {
		try {
			productoServicio.deleteProducto(codigo);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Buscar por categoria", description = "Devuelve productos de una categoria")
	@GetMapping("/categoria/{categoria}")
	public List<Producto> byCategoria(@PathVariable String categoria) {
		return productoServicio.findByCategoria(categoria);
	}

	@Operation(summary = "Buscar por nombre", description = "Busca productos por nombre (query param `q`)")
	@GetMapping("/search")
	public List<Producto> searchByNombre(@RequestParam(name = "q", required = false) String q) {
		return productoServicio.searchByNombre(q);
	}

	@Operation(summary = "Ajustar stock", description = "Ajusta el stock de un producto (delta puede ser negativo)")
	@PatchMapping("/{codigo}/stock")
	public ResponseEntity<?> adjustStock(@PathVariable String codigo, @RequestParam int delta) {
		try {
			Producto updated = productoServicio.adjustStock(codigo, delta);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@Operation(summary = "Cambiar visibilidad", description = "Activa o desactiva la visibilidad de un producto")
	@PatchMapping("/{codigo}/visibility")
	public ResponseEntity<?> setVisibility(@PathVariable String codigo, @RequestParam boolean visible) {
		try {
			Producto updated = productoServicio.setVisibility(codigo, visible);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
