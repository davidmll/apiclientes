package com.projecto.java.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcliente;

	@Column(nullable = false)
	private String nombre;

	private String apellido;
	@Column(nullable = false, unique = true)
	private String email;

	private int telefono;
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	private String imagenes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer" , "handler"})
//	Many = customer y one a region
	private Region region;


//	Method
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

//	Getters and Setters

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getImagenes() {
		return imagenes;
	}

	public void setImagenes(String imagenes) {
		this.imagenes = imagenes;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	

}
