package com.projecto.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.projecto.java.model.Cliente;
import com.projecto.java.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
//	Methods Get
	
	@GetMapping({"/clientes","/todos"})
	public List<Cliente> index(){
		return service.findAllClientes();
	}
	
	@GetMapping("/clientes/{id}")
	public Cliente findClienteById(@PathVariable int id) {
		return service.findById(id);
	}
	
//	Method Post
	
	@PostMapping("/cliente")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente saveCliente(@RequestBody Cliente cliente) {
		return service.saveCliente(cliente);
	}
	
	
//	Method Put
	
	@PutMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente updateCliente(@RequestBody Cliente cliente,@PathVariable int id) {
		
		Cliente clienteUpdate = service.findById(id);
		
		clienteUpdate.setIdcliente(id);
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setCreatedAt(cliente.getCreatedAt());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setTelefono(cliente.getTelefono());
		
		return service.saveCliente(clienteUpdate);
	}
	
	@DeleteMapping("/cliente/{id}")
	public Cliente deleteCliente(@PathVariable int id) {
		Cliente clienteDelete = service.findById(id);
		service.deleteCliente(id);
		return clienteDelete;
	}

}
