package com.projecto.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projecto.java.model.Cliente;
import com.projecto.java.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService service;

//	Methods Get

	@GetMapping({ "/clientes", "/todos" })
	public List<Cliente> index() {
		return service.findAllClientes();
	}

	/*
	 * @GetMapping("/clientes/{id}") public Cliente findClienteById(@PathVariable
	 * int id) { return service.findById(id); }
	 */

	@GetMapping("clientes/{id}")
	public ResponseEntity<?> findClienteById(@PathVariable int id) {

		Cliente cliente = null;

		Map<String, Object> response = new HashMap<>();

		try {

			cliente = service.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}

//	Method Post

	/*@PostMapping("/cliente")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente saveCliente(@RequestBody Cliente cliente) {
		return service.saveCliente(cliente);
	}*/
	
	@PostMapping("/cliente")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;

		Map<String, Object> response = new HashMap<>();

		try {

			clienteNew = service.saveCliente(cliente);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
		response.put("mensaje", clienteNew);
		response.put("cliente", "El cliente ha sido creado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

//	Method Put

	/*@PutMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente updateCliente(@RequestBody Cliente cliente, @PathVariable int id) {

		Cliente clienteUpdate = service.findById(id);

		clienteUpdate.setIdcliente(id);
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setCreatedAt(cliente.getCreatedAt());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setTelefono(cliente.getTelefono());

		return service.saveCliente(clienteUpdate);
	}*/
	
	@PutMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente, @PathVariable int id) {

		Cliente clienteUpdate = service.findById(id);
		
		
		Map<String, Object> response = new HashMap<>();
		
		if(clienteUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el cliente con ID: "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteUpdate.setIdcliente(id);
			clienteUpdate.setApellido(cliente.getApellido());
			clienteUpdate.setCreatedAt(cliente.getCreatedAt());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setNombre(cliente.getNombre());
			clienteUpdate.setTelefono(cliente.getTelefono());
			
			service.saveCliente(clienteUpdate);
		}catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("cliente", clienteUpdate);
		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}


//	Method Delete
	
	/*@DeleteMapping("/cliente/{id}")
	public Cliente deleteCliente(@PathVariable int id) {
		Cliente clienteDelete = service.findById(id);
		service.deleteCliente(id);
		return clienteDelete;
	}*/
	
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable int id) {
		
		Cliente clienteDelete = service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(clienteDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el cliente con ID: "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	
		try {
			service.deleteCliente(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("cliente", clienteDelete);
		response.put("mensaje", "El cliente ha sido eliminado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
