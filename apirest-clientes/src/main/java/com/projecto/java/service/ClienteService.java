package com.projecto.java.service;

import java.util.List;

import com.projecto.java.model.Cliente;
import com.projecto.java.model.Region;

public interface ClienteService {

	public List<Cliente> findAllClientes();

	public Cliente findById(int id);

	public Cliente saveCliente(Cliente cliente);
	
	public void deleteCliente(int id);
	
	public List<Region> findAllRegiones();
}
