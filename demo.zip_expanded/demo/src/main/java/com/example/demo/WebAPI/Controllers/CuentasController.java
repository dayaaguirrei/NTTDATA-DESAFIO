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

import com.example.demo.BusinessLogic.Services.ICuentaService;
import com.example.demo.CoreLogic.Entities.Cuentas;


@RestController
@RequestMapping("/cuentas")

public class CuentasController {
	
	private static final Logger logger = LogManager.getLogger(ClienteController.class);

	@Autowired
	private ICuentaService cuentaService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  findAll() {
		List<Cuentas> listaCuentas = null;	
		Map<String, Object> response = new HashMap<>();
		try {	
			listaCuentas= cuentaService.findAll();	     
		      
		    } catch (Exception e) {
		    	logger.error("Ha ocurrido un error:" + e.getMessage());
		    	response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    	     
		    }	
		if(listaCuentas == null) {
			response.put("mensaje", "No existen datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Cuentas>>(listaCuentas, HttpStatus.OK);
		
		
	}
	
	@GetMapping(value = "/name/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByNombre(@PathVariable("numero") String numero){
		
		Cuentas cuenta = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			cuenta = cuentaService.findbyNoCuenta(numero);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cuenta == null) {
			response.put("mensaje", "Cuenta numero: ".concat(numero.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cuentas>(cuenta, HttpStatus.OK);
	}
	
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveCuentas(@Valid @RequestBody Cuentas request, BindingResult result){
		
		Cuentas cuentaNew = null;
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
			cuentaNew = cuentaService.saveCuentas(request);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cuentaNew == null) {
			response.put("mensaje", "La cuenta NO ha sido creado con éxito!");
		}
		else {
			response.put("mensaje", "La cuenta ha sido creado con éxito!");
		}
		
	
		response.put("cuenta", cuentaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
				
	}
	
	@DeleteMapping(value = "/{cuentaId}/delete")
	public ResponseEntity<Object> deleteCuenta(@PathVariable int cuentaId){
		
		try{			
			cuentaService.deleteCuentas(cuentaId);
			return ResponseEntity.ok(Boolean.TRUE);
		}catch(Exception e) {
			logger.error("Ha ocurrido un error:" + e.getMessage());
			return ResponseEntity.ok(Boolean.FALSE);
		}
		
	}
	
	@PutMapping(value = "/{cuentaId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCuenta(@RequestBody Cuentas request,
			@PathVariable int cuentaId , BindingResult result) {
		
		Cuentas cuentaActual = cuentaService.findbyId(cuentaId);
		Cuentas cuentaUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (cuentaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la cuenta ID: "
					.concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			cuentaUpdated = cuentaService.updateCuentas(request, cuentaId);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cuentaUpdated == null) {
			response.put("mensaje", "El cliente NO ha sido actualizado con éxito!");
		}
		else {
			response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		}
			
		response.put("cliente", cuentaUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
	}
	
	
	
	
}
