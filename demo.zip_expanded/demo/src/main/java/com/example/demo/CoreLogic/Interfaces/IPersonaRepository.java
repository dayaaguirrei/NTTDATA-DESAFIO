package com.example.demo.CoreLogic.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.CoreLogic.Entities.Persona;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Integer>{

	public Persona findByNombre(String nombre); 
	
	public Persona findById(int id);
}
