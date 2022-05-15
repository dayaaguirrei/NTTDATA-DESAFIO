package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.demo.CoreLogic.Entities.Clientes;
import com.example.demo.CoreLogic.Entities.Persona;
import com.example.demo.CoreLogic.Interfaces.IClienteRepository;
import com.example.demo.CoreLogic.Interfaces.IPersonaRepository;
import com.example.demo.WebAPI.Controllers.ClienteController;

public class ClienteControllerTest {
	
	IClienteRepository clienteRepositoryMockito = Mockito.mock(IClienteRepository.class);
	IPersonaRepository personaRepositoryMockito = Mockito.mock(IPersonaRepository.class);
	
	@Autowired
	ClienteController cltecontroller = new ClienteController();
	
	
	@BeforeEach
	void setUp() {
		Clientes mockCliente = new Clientes();
		Persona mockPersona = null;
		
		mockPersona.setDireccion(null);
		mockPersona.setIdenti(null);
		mockPersona.setNombre("Jose Lema");
		mockPersona.setTelefono("098254785");
		mockPersona.setDireccion("OTAVALO SN");
		mockPersona.setEdad(0);
		mockPersona.setGenero("Masculino");		
		mockCliente.setClave("1234");
		mockCliente.setEstado(true);
		mockCliente.setPersona(mockPersona);
		
		
		
	//	Mockito.when(clienteRepositoryMockito.findByNombre("Jose Lema")).thenReturn(mockCliente);
	}
	
	@Test
	void getClienteDetails() {		
		ResponseEntity<?> respuesta;		
		Clientes mockCliente = new Clientes();		
		respuesta = cltecontroller.findByNombre("Jose Lema");
		//Assertions.assertEquals("1234", respuesta.);
		System.out.println(respuesta);
		
	}

}
