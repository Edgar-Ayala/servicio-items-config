package com.formacionbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

//Anotacion para indicar que esta clse es un controlador REST
@RefreshScope
@RestController
public class ItemController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class); //Logger para registrar informacion de la clase
	
	@Autowired
	private CircuitBreakerFactory cbFactory; //Factory para crear CircuitBreakers, inyectado por Spring

	private static Logger log = LoggerFactory.getLogger(ItemController.class); //Logger estatico para registrar informacion de la clase

	@Autowired
	private Environment env;

	//Inyeccion de dependencia del servicio de items utilizando Feign
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	//Inyeccion de valor desde archivo de configuracion
	@Value("${configuracion.texto}")
	private String texto;

	// Método para obtener todos los items
	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name="nombre", required= false) String nombre, @RequestHeader(name="token-request", required = false) String token){
		System.out.println(nombre); //imprime parametro item recibido de la consola
		System.out.println(token);// imprime header 'token-request' recibido en consola
		return itemService.findAll(); //Retorna la lista de items obtenida del servicio
	}

	// Método para obtener un item específico por ID y cantidad, utilizando CircuitBreaker de Resilience4j
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return cbFactory.create("items")
				.run(()-> itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
	}

	// Método alternativo (fallback) para el método detalle en caso de falla del servicio remoto
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}

	// Método para obtener un item específico por ID y cantidad, utilizando CircuitBreaker de Resilience4j con anotación
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name="items")
	@GetMapping("/ver3/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
		// Retorna en CompletableFuture para obtener el resultado de itemService, findById(id, cantidad)
		return CompletableFuture.supplyAsync(()-> itemService.findById(id, cantidad));
	}

	// Método para obtener un item específico por ID y cantidad de manera asíncrona, utilizando CircuitBreaker y TimeLimiter de Resilience4j
	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		logger.info(e.getMessage()); //Registra el mensaje de la excepcion en el logger
		Item item = new Item();
		Producto producto = new Producto();
		//Construye un item de respaldo con valores predeterminados
		item.setCantidad(cantidad); //Configura la cantidad de item alternativo
		producto.setId(id); //Configura el ID del producto alternativo
		producto.setNombre("Camara Sony"); //Configura el nombre del producto alternativo
		producto.setPrecio(500.00); // Configura el precio del producto alternativo
		item.setProducto(producto); // Configura el producto en el item alternativo
		return item; // Retorna el item alternativo
	}

	// Método alternativo (fallback) para el método detalle3 en caso de falla del servicio remoto
	public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {
		logger.info(e.getMessage()); //Loguea el mensaje de error
		Item item = new Item();
		Producto producto = new Producto();
		
		item.setCantidad(cantidad); //Configura la cantidad de item alternativo
		producto.setId(id);//Configura el ID del producto alternativo
		producto.setNombre("Camara Sony"); // Configura el nombre del producto alternativo
		producto.setPrecio(500.00); // Configura el precio del producto alternativo
		item.setProducto(producto); //Configura el producto en el item alternativo
		return CompletableFuture.supplyAsync(()-> item); // Retorna un CompletableFuture con el item alternativo
	}

	// Endpoint para obtener la configuracion del servidor
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}")String puerto){

		log.info(texto); // Loguea el valor inyectado desde la configuracion

		Map<String,String> json = new HashMap<>();
		json.put("texto", texto); // Agrega el valor de 'texto' al JSON de respuesta
		json.put("puerto",puerto); // Agrega el valor del puerto al JSON de respuesta

		// Verifica si el perfil activo es "dev" y agrega propiedades adicionales al JSON de respuesta
		if (env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")){
			json.put("autor.nombre",env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email",env.getProperty("configuracion.autor.email"));
		}
		// Retorna el JSON de respuesta con estado OK (200)
		return new ResponseEntity<Map<String,String>>(json, HttpStatus.OK); //Retorna el JSON de respuesta con estado OK
	}

	// Método para crear un nuevo producto
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {

		return itemService.save(producto);  // Llama al método save del servicio para guardar el producto
	}

	// Método para editar un producto existente por su ID
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
		return itemService.update(producto, id); // Llama al método update del servicio para actualizar el producto

	}

	// Método para eliminar un producto por su ID
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {

		itemService.delete(id); // Llama al método delete del servicio para eliminar el producto por su ID
	}

}
