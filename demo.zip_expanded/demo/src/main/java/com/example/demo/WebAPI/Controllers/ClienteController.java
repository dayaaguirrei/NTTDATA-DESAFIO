package com.example.demo.WebAPI.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessLogic.DTO.CliPerDTO;
import com.example.demo.BusinessLogic.Services.IClienteServices;
import com.example.demo.CoreLogic.Entities.Clientes;
import com.example.demo.CoreLogic.Entities.Persona;
import com.example.demo.utils.MHelpers;


@RestController
@RequestMapping("/clientes")

public class ClienteController {

	private static final Logger logger = LogManager.getLogger(ClienteController.class);
	
	@Autowired
	private IClienteServices clienteService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  findAll() {
		List<Clientes> listaClientes = null;	
		Map<String, Object> response = new HashMap<>();
		try {	
				listaClientes= clienteService.findAll();	     
		      
		    } catch (Exception e) {
		    	logger.error("Ha ocurrido un error:" + e.getMessage());
		    	response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    	     
		    }	
		if(listaClientes == null) {
			response.put("mensaje", "No existen datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Clientes>>(listaClientes, HttpStatus.OK);
		
		
	}
	
	@GetMapping(value = "/name/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByNombre(@PathVariable("nombre") String nombre){
		
		Clientes cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findbyNombre(nombre);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "Cliente con nombre: ".concat(nombre.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Clientes>(cliente, HttpStatus.OK);
	}
	
	@GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findById (@PathVariable("id") int id){
		
		Clientes cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findbyId(id);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "Cliente con el id:  ".concat((int) id + " no existe!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Clientes>(cliente, HttpStatus.OK);
	}
	
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveCliente(@Valid @RequestBody CliPerDTO request, BindingResult result){
	
		CliPerDTO cliper = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {			
			cliper = clienteService.saveClientes(request);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliper == null) {
			response.put("mensaje", "El cliente NO ha sido creado con éxito!");
		}
		else {
			response.put("mensaje", "El cliente ha sido creado con éxito!");
		}
		
		
		Clientes cliente = MHelpers.modelMapper().map(cliper, Clientes.class);
		Persona persona = MHelpers.modelMapper().map(cliper, Persona.class);
		
		response.put("cliente", cliente);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
				
	}
	
	@DeleteMapping(value = "/{clienteId}/delete")
	public ResponseEntity<Object> deleteCliente(@PathVariable int clienteId){
		
		try{			
			clienteService.deleteClientes(clienteId);
			return ResponseEntity.ok(Boolean.TRUE);
		}catch(Exception e) {
			logger.error("Ha ocurrido un error:" + e.getMessage());
			return ResponseEntity.ok(Boolean.FALSE);
		}
		
	}
	
	@PutMapping(value = "/{clienteId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCliente(@RequestBody CliPerDTO request,
			@PathVariable int clienteId , BindingResult result) {
		
		Clientes cliActual = clienteService.findbyId(clienteId);
	
		CliPerDTO cliper = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (cliActual == null) {
			response.put("mensaje", "Error: no se pudo editar, ll cliente ID: "
					.concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			cliper = clienteService.updateClientes(request,clienteId);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliper == null) {
			response.put("mensaje", "El cliente NO ha sido actualizado con éxito!");
		}
		else {
			response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		}
			
		response.put("cliente", cliper);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
	}

	

}
