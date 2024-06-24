package com.formacionbdi.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;

// Interfaz que define un repositorio DAO para la entidad Producto,
// extendiendo CrudRepository que proporciona operaciones CRUD básicas.
public interface ProductoDao extends CrudRepository<Producto, Long>{

}
