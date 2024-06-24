package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity // Indica que esta clase es una entidad JPA y se mapea a una tabla
@Table(name = "productos") // Nombre de la tabla en la base de datos
public class Producto implements Serializable{

	@Id // Indica que este atributo es la clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de la clave primaria
	private Long id;
	
	private String nombre; // Nombre del producto
	private Double precio; // Precio del producto
	
	@Column(name = "create_at") // Mapea el atributo a una columna específica en la tabla
	@Temporal(TemporalType.DATE) // Tipo de dato temporal (fecha)
	private Date createAt; // Fecha de creación del producto
	
	@Transient // Indica que este atributo no se persiste en la base de datos
	private Integer port; // Puerto, utilizado probablemente para fines de red o comunicación

	// Métodos getter y setter para los atributos
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	// Atributo estático para la serialización de la clase
	private static final long serialVersionUID = 1285454306356845809L;

}
