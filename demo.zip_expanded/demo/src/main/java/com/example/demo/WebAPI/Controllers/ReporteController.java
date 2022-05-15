package com.example.demo.WebAPI.Controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessLogic.DTO.ListaMov;
import com.example.demo.CoreLogic.Interfaces.IMovimientosRepository;



@RestController
@RequestMapping("/reportes")
public class ReporteController {	

	private static final Logger logger = LogManager.getLogger(ReporteController.class);
	
	@Autowired
	private IMovimientosRepository moviRepository;
		

	@GetMapping(value = "/lmovimientos/{fechaInicio}/{fechaFin}/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> GeneraReporte(
			@PathVariable String fechaInicio, 
			@PathVariable String fechaFin,
			@PathVariable String idCliente){
		
		List<ListaMov> movi = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			LocalDate localDatei = LocalDate.parse(fechaInicio);
			LocalDate localDatef = LocalDate.parse(fechaFin);
			int idclte = Integer.parseInt(idCliente);
			movi = moviRepository.generateEstadoCuenta(localDatei, localDatef, idclte);
			
			if(movi == null) {
				logger.error("Ha ocurridmovi: "+ movi );
			}
			
			
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(movi == null) {
			response.put("mensaje", "NO existen datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ListaMov>>(movi, HttpStatus.OK);
	}
	
	
}
