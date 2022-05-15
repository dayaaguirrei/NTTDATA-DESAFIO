package com.example.demo.BusinessLogic.Implements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessLogic.Services.IMovimientosServices;
import com.example.demo.CoreLogic.Entities.Cuentas;
import com.example.demo.CoreLogic.Entities.Movimientos;
import com.example.demo.CoreLogic.Interfaces.ICuentaRepository;
import com.example.demo.CoreLogic.Interfaces.IMovimientosRepository;

@Component
public class MovimientosImplements implements IMovimientosServices {

private static final Logger logger = LogManager.getLogger(MovimientosImplements.class);
	
	private static float limiteRetiro=1000;
	@Autowired
	private IMovimientosRepository movimientosRepository;
	@Autowired
	private ICuentaRepository cuentaRepository;
	
	@Override
	public Movimientos findbyNoCuenta(String numero) {
		// TODO Auto-generated method stub
		Movimientos movimiento = new Movimientos();
		try {
			movimiento = movimientosRepository.findBynoCuenta(numero);
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return movimiento;
	}

	@Override
	public List<Movimientos> findAll() {
		// TODO Auto-generated method stub
		List<Movimientos> lmovimiento = new ArrayList<>();
		try {
			lmovimiento = movimientosRepository.findAll();
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return lmovimiento;
	}

	@Override
	public boolean deleteCuentas(int id) {
		// TODO Auto-generated method stub
		boolean isCorrecto = true;
		
		try {
			movimientosRepository.deleteById(id);
			
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			isCorrecto = false;
		}		
		
		return isCorrecto;
	}

	@Override
	public Movimientos updateMovi(Movimientos movi, int MoviId) {
		// TODO Auto-generated method stub
		Movimientos moviUpdate = null;
		try {
			Movimientos movimiento = movimientosRepository.findById(MoviId);
			if(movimiento == null)
			{
				moviUpdate = null;
			}
			else {
				movimiento.setFecha(movi.getFecha());
				movimiento.setEstado(movi.getEstado());
				movimiento.setSaldo(movi.getSaldo());
				movimiento.setTipoMov(movi.getTipoMov());
				movimiento.setValor(movi.getValor());
				moviUpdate = movimientosRepository.save(movimiento);	
			}
					
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			
		}		
		return moviUpdate;
	}

	@Override
	public Movimientos saveMovi(Movimientos movi) {
		// TODO Auto-generated method stub
		Movimientos moviInsert = null;
		try {
			Cuentas cuentas = cuentaRepository.findBynoCuenta(movi.getNoCuenta());
			logger.error("Ha dcsff un error: "+ cuentas.getNoCuenta());
			if(cuentas != null)
			{
				movi.setCtas(cuentas);
				moviInsert = movimientosRepository.save(movi);	
			}
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			
		}		
		return moviInsert;
	}

	@Override
	public Movimientos findbyId(int id) {
		// TODO Auto-generated method stub
		Movimientos movimiento = new Movimientos();
		try {
			movimiento = movimientosRepository.findById(id);
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return movimiento;
	}

	@Override
	public String validaciones(Movimientos movi) {
		// TODO Auto-generated method stub
		String mensaje="";
		// 1 credito
		// 2 debito
		if(movi.getTipoMov()==2) {
			// validar si tiene dinero
			Cuentas ctas =null;
			float saldoini = 0;
			ctas = cuentaRepository.findBynoCuenta(movi.getNoCuenta());
			if(ctas == null) {
				logger.error("Ha no existe: ");
				mensaje="Cuenta no existe";
			}
			else {
				if(ctas.getEstado()) {
					saldoini=ctas.getSaldoInicial();
					if(saldoini <= 0)
					{
						mensaje="Saldo no disponible";
					}
					else {
						// sumar todos los mov
						float total = getDebitosDiarios()+movi.getValor();
						if(total > limiteRetiro) {
							mensaje="Cupo diario excedido";
						}
						else {
							mensaje = "OK";
						}
						
					}
				}				
				
			}						
			
		}
		return mensaje;
			
		
		
	}

	@Override
	public float getDebitosDiarios() {
		// TODO Auto-generated method stub
		List<Movimientos> movs = new ArrayList<>();
		float debitos =0;		
		movs=movimientosRepository.findByFecha(LocalDate.now());
		for (int i=0;i<movs.size();i++) {
			if(movs.get(i).getTipoMov()==2)
			{
				debitos += movs.get(i).getValor();
			}
		}
		return debitos;
		
	}

}
