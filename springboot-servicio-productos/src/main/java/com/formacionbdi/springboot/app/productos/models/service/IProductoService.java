package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;

// Interfaz que define los métodos para operaciones de productos
public interface IProductoService {

	// Método para obtener todos los productos
	public List<Producto> findAll();

	// Método para obtener un producto por su ID
	public Producto findById(Long id);

	public Producto save(Producto producto);

	public void deleteById(Long id);

}
