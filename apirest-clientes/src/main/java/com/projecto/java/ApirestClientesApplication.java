package com.projecto.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API CLientes",version="1.0",description = "Crud completo de clientes"))
public class ApirestClientesApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(ApirestClientesApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		String pass = "12345";
		for(int i = 0; i<3;i++) {
			String passBCrypt = passwordEncoder.encode(pass);
			System.err.println(passBCrypt);
		}
		
	}

}
