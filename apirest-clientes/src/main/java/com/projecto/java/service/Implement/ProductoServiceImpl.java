package com.projecto.java.service.Implement;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projecto.java.Dao.ProductoRepository;
import com.projecto.java.model.Producto;
import com.projecto.java.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository repo;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAllProductos() {
		
		return (List<Producto>) repo.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Producto findById(int id) {
		
		return repo.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Producto saveProducto(Producto producto) {
		
		return repo.save(producto);
	}
	

	@Override
	public void deleteProducto(int id) {
		repo.deleteById(id);
	}
	
	

}
