package com.example.demo.BusinessLogic.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class listaMovimientosDTO {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaini;
		
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechafin;
	
	
	private String nombre;
	
	private int idclte;


	public LocalDate getFechaini() {
		return fechaini;
	}


	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}


	public LocalDate getFechafin() {
		return fechafin;
	}


	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getIdclte() {
		return idclte;
	}


	public void setIdclte(int idclte) {
		this.idclte = idclte;
	}

}
