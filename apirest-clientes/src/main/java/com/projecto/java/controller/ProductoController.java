package com.projecto.java.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projecto.java.model.Producto;
import com.projecto.java.service.ProductoService;

@RestController
@RequestMapping("/task")
public class ProductoController {

	@Autowired
	private ProductoService service;

//	Methods Get

	@GetMapping({ "/productos", "/todos" })
	public List<Producto> index() {
		return service.findAllProductos();
	}


	@GetMapping("productos/{id}")
	public ResponseEntity<?> findProductoById(@PathVariable int id) {

		Producto producto = null;

		Map<String, Object> response = new HashMap<>();

		try {

			producto = service.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("mensaje", "El producto ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Producto>(producto, HttpStatus.OK);

	}

//	Method Post
	
	@PostMapping("/producto")
	public ResponseEntity<?> saveProducto(@RequestBody Producto producto) {
		Producto productoNew = null;

		Map<String, Object> response = new HashMap<>();

		try {

			productoNew = service.saveProducto(producto);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto.getCodigo() > 5) {
			response.put("mensaje", "El codigo no puede superar los 5 digitos ");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("mensaje", productoNew);
		response.put("cliente", "El producto ha sido creado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

//	Method Put
	
	@PutMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateProducto(@RequestBody Producto producto, @PathVariable int id) {

		Producto productoUpdate = service.findById(id);
		
		
		Map<String, Object> response = new HashMap<>();
		
		if(productoUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el producto con ID: "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(producto.getCodigo() > 5) {
			response.put("mensaje", "El codigo no puede superar los 5 digitos ");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			productoUpdate.setIdproducto(id);
			productoUpdate.setCodigo(producto.getCodigo());
			productoUpdate.setTipo(producto.getTipo());
			productoUpdate.setCantidad(producto.getCantidad());
			productoUpdate.setPrecio(producto.getPrecio());
			productoUpdate.setMarca(producto.getMarca());
			productoUpdate.setFecha_ingreso(producto.getFecha_ingreso());
			productoUpdate.setDescripcion(producto.getDescripcion());
			
			service.saveProducto(productoUpdate);
		}catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("cliente", productoUpdate);
		response.put("mensaje", "El producto ha sido actualizado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


//	Method Delete
	
	
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> deleteProducto(@PathVariable int id) {
		
		Producto productoDelete = service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(productoDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el producto con ID: "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	
		try {
			service.deleteProducto(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("cliente", productoDelete);
		response.put("mensaje", "El producto ha sido eliminado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
