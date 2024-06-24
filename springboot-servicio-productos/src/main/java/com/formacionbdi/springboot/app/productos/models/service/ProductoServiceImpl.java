package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.dao.ProductoDao;
import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Anotación para marcar esta clase como un servicio de Spring
@Service
public class ProductoServiceImpl implements IProductoService{

	// Inyección de dependencia del ProductoDao
	@Autowired
	private ProductoDao productoDao;

	// Método para obtener todos los productos
	@Override
	@Transactional(readOnly = true) // Transacción en modo solo lectura
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll(); // Llama al método findAll() del ProductoDao para obtener todos los productos

	}

	// Método para obtener un producto por su ID
	@Override
	@Transactional(readOnly = true) // Transacción en modo solo lectura
	public Producto findById(Long id) {
		// Llama al método findById() del ProductoDao para buscar un producto por su ID
		// Si no se encuentra el producto, devuelve null
		return productoDao.findById(id).orElse(null);
	}

	// CRUD
	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoDao.deleteById(id);
	}


}
