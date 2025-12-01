package com.cienmilsabores.backendcienmil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cienmilsabores.backendcienmil.model.Producto;
import com.cienmilsabores.backendcienmil.repository.ProductoRepository;

@Service
public class ProductoServicio {

	private final ProductoRepository productoRepository;

	public ProductoServicio(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	public List<Producto> listAll() {
		return productoRepository.findAll();
	}

	public Optional<Producto> findByCodigo(String codigo) {
		return productoRepository.findById(codigo);
	}

	@Transactional
	public Producto createProducto(Producto producto) {
		if (producto == null) throw new IllegalArgumentException("Producto no puede ser null");
		if (producto.getCodigo() == null || producto.getCodigo().isBlank()) {
			throw new IllegalArgumentException("El codigo del producto es obligatorio");
		}
		if (productoRepository.existsById(producto.getCodigo())) {
			throw new IllegalArgumentException("Producto con codigo ya existe: " + producto.getCodigo());
		}
		return productoRepository.save(producto);
	}

	@Transactional
	public Producto updateProducto(String codigo, Producto producto) {
		Producto existing = productoRepository.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigo));

		// update fields (keep codigo)
		existing.setNombre(producto.getNombre());
		existing.setDescripcion(producto.getDescripcion());
		existing.setCategoria(producto.getCategoria());
		existing.setImagenUrl(producto.getImagenUrl());
		existing.setPrecio(producto.getPrecio());
		existing.setVisible(producto.isVisible());
		existing.setStock(producto.getStock());

		return productoRepository.save(existing);
	}

	@Transactional
	public void deleteProducto(String codigo) {
		if (!productoRepository.existsById(codigo)) {
			throw new IllegalArgumentException("Producto no encontrado: " + codigo);
		}
		productoRepository.deleteById(codigo);
	}

	public List<Producto> findByCategoria(String categoria) {
		if (categoria == null || categoria.isBlank()) return List.of();
		return productoRepository.findAll().stream()
				.filter(p -> categoria.equalsIgnoreCase(p.getCategoria()))
				.collect(Collectors.toList());
	}

	public List<Producto> searchByNombre(String term) {
		if (term == null || term.isBlank()) return List.of();
		String q = term.toLowerCase();
		return productoRepository.findAll().stream()
				.filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(q))
				.collect(Collectors.toList());
	}

	@Transactional
	public Producto adjustStock(String codigo, int delta) {
		Producto existing = productoRepository.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigo));
		int newStock = existing.getStock() + delta;
		if (newStock < 0) newStock = 0;
		existing.setStock(newStock);
		return productoRepository.save(existing);
	}

	@Transactional
	public Producto setVisibility(String codigo, boolean visible) {
		Producto existing = productoRepository.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigo));
		existing.setVisible(visible);
		return productoRepository.save(existing);
	}
}
