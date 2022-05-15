package com.example.demo.BusinessLogic.DTO;

import java.time.LocalDate;

public class ListaMov {
 
 public ListaMov(LocalDate fecha, float saldo, float valor, String noCuenta, float saldoInicial,
		String tipoCuenta, String nombre) {
	super();
	this.fecha = fecha;
	this.saldo = saldo;
	this.valor = valor;
	this.noCuenta = noCuenta;
	this.saldoInicial = saldoInicial;
	this.tipoCuenta = tipoCuenta;
	this.nombre = nombre;
}
 
 private LocalDate fecha;

private float saldo;
 
 private float valor;
 
 private String noCuenta;
 
 private float saldoInicial;
 
 private String tipoCuenta;
 
 private String nombre;

public LocalDate getFecha() {
	return fecha;
}

public void setFecha(LocalDate fecha) {
	this.fecha = fecha;
}

public float getSaldo() {
	return saldo;
}

public void setSaldo(float saldo) {
	this.saldo = saldo;
}

public float getValor() {
	return valor;
}

public void setValor(float valor) {
	this.valor = valor;
}


public String getNoCuenta() {
	return noCuenta;
}

public void setNoCuenta(String noCuenta) {
	this.noCuenta = noCuenta;
}

public float getSaldoInicial() {
	return saldoInicial;
}

public void setSaldoInicial(float saldoInicial) {
	this.saldoInicial = saldoInicial;
}

public String getTipoCuenta() {
	return tipoCuenta;
}

public void setTipoCuenta(String tipoCuenta) {
	this.tipoCuenta = tipoCuenta;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}
 
}
