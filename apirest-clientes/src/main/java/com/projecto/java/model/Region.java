package com.projecto.java.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "regiones")
public class Region implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idregion;

	private String nombre;

	public int getIdregion() {
		return idregion;
	}

	public void setIdregion(int idregion) {
		this.idregion = idregion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
