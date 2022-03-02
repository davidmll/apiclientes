package com.projecto.java.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projecto.java.model.Cliente;
import com.projecto.java.model.Region;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

	@Query("from Region")
	public List<Region> findAllRegions();
}
