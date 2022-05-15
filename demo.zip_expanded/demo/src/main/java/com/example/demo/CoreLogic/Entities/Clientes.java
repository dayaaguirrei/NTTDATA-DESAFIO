package com.example.demo.CoreLogic.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_CLIENTES")
public class Clientes implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cliente_id", unique = true, nullable = false)	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "cliente_clave" , length = 20)	
	private String clave;
	
	@Column(name = "cliente_estado" , nullable=false)	
	private Boolean estado;
		
	@OneToOne
	@JoinColumn(name="persona_id")
	private Persona persona;
	
	
	public int getCid() {
		return cid;
	}



	public void setCid(int cid) {
		this.cid = cid;
	}



	public Persona getPersona() {
		return persona;
	}



	public void setPersona(Persona persona) {
		this.persona = persona;
	}



	public Clientes() {
		super();
	}


	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}


}
