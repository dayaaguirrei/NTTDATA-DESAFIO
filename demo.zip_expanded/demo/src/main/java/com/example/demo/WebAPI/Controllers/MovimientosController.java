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
import com.example.demo.BusinessLogic.Services.IMovimientosServices;
import com.example.demo.CoreLogic.Entities.Cuentas;
import com.example.demo.CoreLogic.Entities.Movimientos;


@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
	
	private static final Logger logger = LogManager.getLogger(ClienteController.class);

	@Autowired
	private IMovimientosServices moviService;
	
	@Autowired
	private ICuentaService cuentaService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  findAll() {
		List<Movimientos> listMovi = null;	
		Map<String, Object> response = new HashMap<>();
		try {	
			listMovi= moviService.findAll();	     
		      
		    } catch (Exception e) {
		    	logger.error("Ha ocurrido un error:" + e.getMessage());
		    	response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    	     
		    }	
		if(listMovi == null) {
			response.put("mensaje", "No existen datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Movimientos>>(listMovi, HttpStatus.OK);
		
		
	}
	
	@GetMapping(value = "/name/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByNombre(@PathVariable("numero") String numero){
		
		Movimientos movi = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			movi = moviService.findbyNoCuenta(numero);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(movi == null) {
			response.put("mensaje", "Cuenta numero: ".concat(numero.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Movimientos>(movi, HttpStatus.OK);
	}
	
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveMovimientos(@Valid @RequestBody Movimientos request, BindingResult result){
		float saldoinicial = 0;
		Movimientos movNew = null;
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
			String mensaje = moviService.validaciones(request);
			
			if(mensaje == "OK") {
				
				Cuentas cta=null;
				cta = cuentaService.findbyNoCuenta(request.getNoCuenta());
				
				if(request.getTipoMov()==1) {
					saldoinicial = cta.getSaldoInicial() + request.getValor();
					cta.setSaldoInicial(saldoinicial);
				//	cuentaService.updateCuentas(cta, cta.getId());
				}
				else {
					saldoinicial = cta.getSaldoInicial() - request.getValor();
					cta.setSaldoInicial(saldoinicial);
				//	cuentaService.updateCuentas(cta, cta.getId());
				}
				
				request.setSaldo(saldoinicial);
				movNew = moviService.saveMovi(request);
				if(movNew == null) {
					response.put("mensaje", "El movimiento NO ha sido creado con éxito!");
				}
				else {
					response.put("mensaje", "El movimiento ha sido creado con éxito!");
				}
				
			}
			else {
				response.put("mensaje", mensaje);
			}
			
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
		response.put("cliente", movNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
				
	}
	
	@DeleteMapping(value = "/{moviId}/delete")
	public ResponseEntity<Object> deleteMovi(@PathVariable int moviId){
		
		try{			
			moviService.deleteCuentas(moviId);
			return ResponseEntity.ok(Boolean.TRUE);
		}catch(Exception e) {
			logger.error("Ha ocurrido un error:" + e.getMessage());
			return ResponseEntity.ok(Boolean.FALSE);
		}
		
	}
	
	@PutMapping(value = "/{moviId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateMovi(@RequestBody Movimientos request,
			@PathVariable int moviId , BindingResult result) {
		
		Movimientos moviActual = moviService.findbyId(moviId);
		Movimientos moviUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (moviActual == null) {
			response.put("mensaje", "Error: no se pudo editar, ll cliente ID: "
					.concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			moviUpdated = moviService.updateMovi(request, moviId);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(moviUpdated == null) {
			response.put("mensaje", "El movimiento NO ha sido actualizado con éxito!");
		}
		else {
			response.put("mensaje", "El movimiento ha sido actualizado con éxito!");
		}
			
		response.put("cliente", moviUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
	}
	

}
