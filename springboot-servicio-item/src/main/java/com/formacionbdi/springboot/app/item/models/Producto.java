package com.formacionbdi.springboot.app.item.models;

import java.util.Date;

public class Producto {
	
	private Long id; //Identificador unico del producto
	private String nombre; //Nombre del producto
	private Double precio; //Precio del producto
	private Date createAt; // Fecha de creacion del producto
	private Integer port; //Puerto asociado al servicio del producto

	//Metodos getter and setter para el campo 'id'
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//Metodos getter and setter para el campo 'nombre'
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Metodos getter and setter para el campo 'precio'
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	//Metodos getter and setter para el campo 'createAt'
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	//Metodos getter and setter para el campo 'port'
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	

}
