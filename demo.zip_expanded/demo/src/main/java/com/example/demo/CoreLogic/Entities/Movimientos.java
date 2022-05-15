package com.example.demo.CoreLogic.Entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TB_MOVIMIENTOS")
public class Movimientos {
	
	@Id
	@Column(name = "movimientos_id", unique = true, nullable = false)	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "movimientos_fecha", nullable=false)	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	
	@NotEmpty(message ="no puede estar vacio")
	@Column(name = "movimientos_nocuenta" , length = 50 , nullable=false)	
	private String noCuenta;
	
	
	@Column(name = "movimientos_tipomov" , nullable=false)	
	private int tipoMov;
	
	@Column(name = "movimientos_valor" , nullable=false)	
	private float valor;
	
	@Column(name = "movimientos_saldo" , nullable=false)	
	private float saldo;
	
	@Column(name = "movimientos_estado" , nullable=false)	
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_id")
	private Cuentas ctas;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getNoCuenta() {
		return noCuenta;
	}

	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
	}

	public int getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(int tipoMov) {
		this.tipoMov = tipoMov;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Cuentas getCtas() {
		return ctas;
	}

	public void setCtas(Cuentas ctas) {
		this.ctas = ctas;
	}

}
