package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import com.formacionbdi.springboot.app.item.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service("serviceFeign") // Esto permite que Spring gestione esta clase como un componente dentro del contexto de la aplicacion.
public class ItemServiceFeign implements ItemService {
	
	@Autowired // Esta clase es una interface generada automaticamente por Feign a partir de una interfaz Java que define metodos para llamar a servicios REST.
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		//utilliza feign para obtener la lista de productos y los convierte en items con cantidad 1.
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		//Utiliza feign para obtener un producto por su id y crea un Item con la cantidad especificada.
		return new Item(clienteFeign.detalle(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		//no impllementado en este contexto, ya que no se utiliza feign para operaciones de escritura.
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		//No implementado en este contexto, ya que no se utiliza feign para operaciones de escritura.
		return clienteFeign.update(producto,id);
	}

	@Override
	//No implementado en este contexto, ya que no se utiliza feign para operaciones de escritura.
	public void delete(Long id) {
		clienteFeign.eliminar(id);

	}

}
