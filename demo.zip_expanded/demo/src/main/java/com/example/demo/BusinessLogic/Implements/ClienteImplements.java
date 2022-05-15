package com.example.demo.BusinessLogic.Implements;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessLogic.DTO.CliPerDTO;
import com.example.demo.BusinessLogic.Services.IClienteServices;
import com.example.demo.CoreLogic.Entities.Clientes;
import com.example.demo.CoreLogic.Entities.Persona;
import com.example.demo.CoreLogic.Interfaces.IClienteRepository;
import com.example.demo.CoreLogic.Interfaces.IPersonaRepository;
import com.example.demo.utils.MHelpers;

@Component
public class ClienteImplements implements IClienteServices {
	
	private static final Logger logger = LogManager.getLogger(ClienteImplements.class);
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IPersonaRepository personaRepository;
		

	@Override
	public Clientes findbyNombre(String nombre) {
		// TODO Auto-generated method stub
		Clientes cli = new Clientes();
		Persona per =null;
		
		try {
			per = personaRepository.findByNombre(nombre);
			cli= clienteRepository.findByPersona(per.getpId());			
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		
		return cli;
		
	}

	@Override
	public List<Clientes> findAll() {
		// TODO Auto-generated method stub
		List<Clientes> lcli = new ArrayList<>();	
		try {
			lcli = clienteRepository.findAll();
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return lcli;
	}

	@Override
	public boolean deleteClientes(int id) {
		// TODO Auto-generated method stub
		boolean isCorrecto = true;
		
		try {
			clienteRepository.deleteById(id);
			
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			isCorrecto = false;
		}		
		
		return isCorrecto;
	}

	@Override
	public CliPerDTO updateClientes(CliPerDTO cli, int clienteId) {
		// TODO Auto-generated method stub
		CliPerDTO cliper = null;
		Clientes cliUpdate = null;
		try {
			Clientes clie = clienteRepository.findById(clienteId);			
			
						
			if(clie != null) {
				/*
				 * Persona pers1= pers; pers1.setDireccion(cli.getDireccion());
				 * pers1.setEdad(cli.getEdad()); pers1.setGenero(cli.getGenero());
				 * pers1.setIdenti(cli.getIdenti()); pers1.setNombre(cli.getNombre());
				 * pers1.setTelefono(cli.getTelefono()); perUpdate =
				 * personaRepository.save(pers1);
				 */
				Clientes clie1= clie;
				clie1.setClave(cli.getClave());				
				clie1.setEstado(cli.getEstado());
				//clie1.setPersona(perUpdate);
				cliUpdate = clienteRepository.save(clie1);	
				
				cliper = com.example.demo.utils.MHelpers.modelMapper().map(cliUpdate, CliPerDTO.class);	
			}
					
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			
		}		
		return cliper;
	}

	@Override
	public CliPerDTO saveClientes(CliPerDTO cli) {
		// TODO Auto-generated method stub
		CliPerDTO cliper = null;
		Clientes cliInsert = null;
		Persona perInsert= null;
		try {
			cliInsert = clienteRepository.findById(cli.getCid());
			perInsert = personaRepository.findById(cli.getPersona().getpId());
			
			if (cli.getCid() == 0) {
				if (cli.getPid() == 0) {
					Clientes cliente = MHelpers.modelMapper().map(cli, Clientes.class);
					Persona persona = MHelpers.modelMapper().map(cli, Persona.class);
					persona.setDireccion(cli.getDireccion());
					persona.setEdad(cli.getEdad());
					persona.setGenero(cli.getGenero());
					persona.setIdenti(cli.getIdenti());
					persona.setNombre(cli.getNombre());
					persona.setTelefono(cli.getTelefono());
					perInsert = personaRepository.save(persona);
					
					cliente.setClave(cli.getClave());				
					cliente.setEstado(cli.getEstado());
					cliente.setPersona(persona);
					cliInsert = clienteRepository.save(cliente);	
					
					cliper = com.example.demo.utils.MHelpers.modelMapper().map(cliInsert, CliPerDTO.class);
					
				} 
			}
			
			
			
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());			
		}		
		return cliper;
	}

	@Override
	public Clientes findbyId(int id) {
		// TODO Auto-generated method stub
		Clientes cli =  new Clientes(); 
		try {
			cli = clienteRepository.findById(id);
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}				
		return  cli;
	}

}
