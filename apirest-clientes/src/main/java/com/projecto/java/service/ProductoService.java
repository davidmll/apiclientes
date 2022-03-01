package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Producto;

public interface ProductoService {

	List<Producto> findAllProductos();

	Producto findById(int id);

	Producto saveProducto(Producto producto);

	void deleteProducto(int id);

}