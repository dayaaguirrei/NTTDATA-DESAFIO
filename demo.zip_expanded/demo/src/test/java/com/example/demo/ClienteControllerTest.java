package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.CoreLogic.Entities.Clientes;
import com.example.demo.CoreLogic.Entities.Persona;
import com.example.demo.CoreLogic.Interfaces.IClienteRepository;
import com.example.demo.WebAPI.Controllers.ClienteController;

public class ClienteControllerTest {
	
	IClienteRepository clienteRepositoryMockito = Mockito.mock(IClienteRepository.class);
	
	@Autowired
	ClienteController cltecontroller = new ClienteController();
	
	
	@BeforeEach
	void setUp() {
		Clientes mockCliente = new Clientes();
		Persona mockPersona= new Persona();		
		mockPersona.setDireccion("Amazonas y NNUU");
		mockPersona.setIdenti("123456789");
		mockPersona.setNombre("Marianela Montalvo");
		mockPersona.setTelefono("097548965");
		mockPersona.setEdad(30);
		mockPersona.setGenero("Femenino");		
		mockCliente.setClave("5678");
		mockCliente.setEstado(true);
		mockCliente.setPersona(mockPersona);	
	    Mockito.when(clienteRepositoryMockito.findById(1)).thenReturn(mockCliente);
	}
	
	@Test
	void getClienteDetails() {		
		Clientes respuesta;			
		respuesta = clienteRepositoryMockito.findById(1);
		Assertions.assertEquals("5678", respuesta.getClave());
		System.out.println(respuesta);
		
	}

}
