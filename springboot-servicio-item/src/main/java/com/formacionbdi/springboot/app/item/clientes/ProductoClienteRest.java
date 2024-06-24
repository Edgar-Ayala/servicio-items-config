package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.formacionbdi.springboot.app.item.models.Producto;

// Anotación FeignClient para especificar el nombre del servicio remoto
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	// Método para obtener todos los productos del servicio remoto
	@GetMapping("/listar")
	public List<Producto> listar();

	// Método para obtener un producto específico por su ID del servicio remoto
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);

	// Método para crear un nuevo producto
	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);

	// Método para actualizar un producto existente por su ID
	@PutMapping("/editar/{id}")
	public Producto update(@RequestBody Producto producto, @PathVariable Long id);

	// Método para eliminar un producto por su ID
	@DeleteMapping("/eliminar/{id}")
	public void eliminar (@PathVariable Long id);
}
