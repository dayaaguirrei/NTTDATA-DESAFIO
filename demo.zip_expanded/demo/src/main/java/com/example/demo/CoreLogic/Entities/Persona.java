package com.example.demo.CoreLogic.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_PERSONAS")
public class Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "persona_id", unique = true, nullable = false)	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pid;
	
	@NotEmpty(message ="no puede estar vacio")
	@Size(min=3, max=100, message="Debe tener minimo 3 caracteres")
	@Column(name = "persona_nombre" , length = 100 , nullable=false)	
	private String nombre;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "persona_genero" , length = 20 )	
	private String genero;
	
	@Column(name = "persona_edad" , length = 3)	
	private int edad;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "persona_identi" , length = 20)	
	private String identi;
	
	@Column(name = "persona_direccion" , length = 100)	
	private String direccion;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "persona_telefono" , length = 20)	
	private String telefono;

	public int getpId() {
		return pid;
	}

	public void setpId(int id) {
		this.pid = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getIdenti() {
		return identi;
	}

	public void setIdenti(String identi) {
		this.identi = identi;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
}
