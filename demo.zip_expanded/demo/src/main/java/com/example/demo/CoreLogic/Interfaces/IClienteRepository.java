package com.example.demo.CoreLogic.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.CoreLogic.Entities.Clientes;

@Repository
public interface IClienteRepository  extends JpaRepository<Clientes, Integer>{ 
	
		
	public Clientes findByPersona(int id);
	
	public Clientes findById(int id);
	
}
