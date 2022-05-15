package com.example.demo.CoreLogic.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.CoreLogic.Entities.Cuentas;

@Repository
public interface ICuentaRepository extends JpaRepository<Cuentas, Integer> {

	public Cuentas findBynoCuenta(String noCuenta);
	
	public Cuentas findById(int id);
	

}
