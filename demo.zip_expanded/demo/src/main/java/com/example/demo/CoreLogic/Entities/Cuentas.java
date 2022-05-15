package com.example.demo.CoreLogic.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "TB_CUENTAS")
public class Cuentas {
	
	@Id
	@Column(name = "cuentas_id", unique = true, nullable = false)	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "cuentas_nocuenta" , unique = true, length = 50 , nullable=false)	
	private String noCuenta;
	
	
	@Column(name = "cuentas_tipocuenta" , nullable=false)	
	private int tipoCuenta;
	
	@Column(name = "cuentas_saldoini" , nullable=false)	
	private float saldoInicial;

	@Column(name = "cuentas_estado" , nullable=false)	
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Clientes cliente;


	public Clientes getCliente() {
		return cliente;
	}


	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNoCuenta() {
		return noCuenta;
	}


	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
	}


	public int getTipoCuenta() {
		return tipoCuenta;
	}


	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}


	public float getSaldoInicial() {
		return saldoInicial;
	}


	public void setSaldoInicial(float saldoInicial) {
		this.saldoInicial = saldoInicial;
	}


	public boolean getEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


}
