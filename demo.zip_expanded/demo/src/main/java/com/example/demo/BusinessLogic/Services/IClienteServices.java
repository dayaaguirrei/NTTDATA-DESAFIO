package com.example.demo.BusinessLogic.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessLogic.DTO.CliPerDTO;
import com.example.demo.CoreLogic.Entities.Clientes;

@Service
public interface IClienteServices {

	public Clientes findbyNombre(String nombre);
	
	public Clientes findbyId(int id);
	
	public List<Clientes> findAll();
	
	public boolean deleteClientes(int id);
	
	public CliPerDTO updateClientes(CliPerDTO cli, int clienteId);
	
	public CliPerDTO saveClientes(CliPerDTO cli);
	
}
