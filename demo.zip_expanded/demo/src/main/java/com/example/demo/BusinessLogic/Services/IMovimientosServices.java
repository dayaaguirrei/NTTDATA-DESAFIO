package com.example.demo.BusinessLogic.Services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.CoreLogic.Entities.Movimientos;

@Service
public interface IMovimientosServices {

	public Movimientos findbyNoCuenta(String numero);

	public List<Movimientos> findAll();
	
	public boolean deleteCuentas(int id);
	
	public Movimientos updateMovi(Movimientos movi, int MoviId);
	
	public Movimientos saveMovi(Movimientos movi);
	
	public Movimientos findbyId(int id);
	
	public String validaciones(Movimientos movi);
	
	public float getDebitosDiarios();
	
}
