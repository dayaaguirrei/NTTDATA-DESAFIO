package com.example.demo.BusinessLogic.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.CoreLogic.Entities.Cuentas;

@Service
public interface ICuentaService {

	public Cuentas findbyNoCuenta(String numero);
	
	public List<Cuentas> findAll();
	
	public boolean deleteCuentas(int id);
	
	public Cuentas updateCuentas(Cuentas cuenta, int cuentasId);
	
	public Cuentas saveCuentas(Cuentas cuenta);
	
	public Cuentas findbyId(int id);
	
	
	
}
