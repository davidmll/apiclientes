package com.projecto.java.service.Implement;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projecto.java.Dao.ClienteRepository;
import com.projecto.java.model.Cliente;
import com.projecto.java.model.Region;
import com.projecto.java.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAllClientes() {
		
		return (List<Cliente>) repo.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(int id) {
		
		return repo.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Cliente saveCliente(Cliente cliente) {
		
		return repo.save(cliente);
	}
	

	@Override
	public void deleteCliente(int id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		
		return repo.findAllRegions();
	}
	
	

}
