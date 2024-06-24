package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env; // Para acceder a propiedades del entorno
	
	@Value("${server.port}")
	private Integer port; // Valor del puerto del servidor
	
	@Autowired
	private IProductoService productoService; // Servicio para operaciones con productos
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		// Endpoint para listar todos los productos
		return productoService.findAll().stream().map(producto ->{
			producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); // Configura el puerto en cada producto
			//producto.setPort(port); // Configura el puerto usando la propiedad @Value
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		// Endpoint para obtener detalles de un producto por su ID

		if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado!"); // Simula un escenario de producto no encontrado
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L); // Simula un retardo de 5 segundos para el producto con ID 7
		}
		
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); // Configura el puerto en el producto
		//producto.setPort(port); // Configura el puerto usando la propiedad @Value
				
		return producto;


	}

	// Método para crear un nuevo producto
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}

	// Método para editar un producto existente por su ID
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		// Busca el producto por su ID
		Producto productoDb = productoService.findById(id);

		// Actualiza los datos del producto con los nuevos datos recibidos
		productoDb.setNombre(producto.getNombre());
		productoDb.setPrecio(producto.getPrecio());

		// Guarda el producto actualizado y devuelve el producto actualizado
		return productoService.save(productoDb);
	}

	// Método para eliminar un producto por su ID
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		// Elimina el producto por su ID
		productoService.deleteById(id);
	}
}
