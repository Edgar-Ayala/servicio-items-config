package com.formacionbdi.springboot.app.item.models;

public class Item {

	private Producto producto; // Representa el producto asociado al item
	private Integer cantidad; // Representa a cantidad de este producto en el item

	public Item() {
	}

	//Constructor que inicializa un item con un producto y una cantidad
	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	//Metodos getter y setter para el campo 'producto'
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	//Metodos getter y setter para el campo 'cantidad'
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	//Metodo para calcular el total del item (precio del producto * cantidad)
	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}

}
