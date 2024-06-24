package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

public interface ItemService {

	// Método para obtener todos los ítems
	public List<Item> findAll();

	// Método para obtener un ítem por su ID y cantidad
	public Item findById(Long id, Integer cantidad);

	// Método para guardar un producto
	public Producto save(Producto producto);

	// Método para actualizar un producto por su ID
	public Producto update(Producto producto, Long id);

	// Método para eliminar un producto por su ID
	public void delete(Long id);
}
