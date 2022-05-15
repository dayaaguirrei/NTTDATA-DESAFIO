package com.example.demo.BusinessLogic.Implements;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.BusinessLogic.Services.ICuentaService;
import com.example.demo.CoreLogic.Entities.Clientes;
import com.example.demo.CoreLogic.Entities.Cuentas;
import com.example.demo.CoreLogic.Interfaces.IClienteRepository;
import com.example.demo.CoreLogic.Interfaces.ICuentaRepository;

@Component
public class CuentaImplements implements ICuentaService {
	
private static final Logger logger = LogManager.getLogger(CuentaImplements.class);
	
	@Autowired
	private ICuentaRepository cuentaRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	public Cuentas findbyNoCuenta(String numero) {
		// TODO Auto-generated method stub
		Cuentas cuenta = new Cuentas();
		try {
			cuenta = cuentaRepository.findBynoCuenta(numero);
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return cuenta;
	}

	@Override
	public List<Cuentas> findAll() {
		// TODO Auto-generated method stub
		List<Cuentas> lcuenta = new ArrayList<>();
		try {
			lcuenta = cuentaRepository.findAll();
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return lcuenta;
	}

	@Override
	public boolean deleteCuentas(int id) {
		// TODO Auto-generated method stub
		boolean isCorrecto = true;
		
		try {
			cuentaRepository.deleteById(id);
			
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			isCorrecto = false;
		}		
		
		return isCorrecto;
	}

	@Override
	public Cuentas updateCuentas(Cuentas cuenta, int cuentasId) {
		// TODO Auto-generated method stub
		Cuentas cuentasUpdate = null;
		try {
			Cuentas cuentas = cuentaRepository.findById(cuentasId);
			if(cuentas==null) {
				cuentasUpdate = null;
			}else {
				cuentas.setNoCuenta(cuenta.getNoCuenta()); 
				cuentas.setSaldoInicial(cuenta.getSaldoInicial());
				cuentas.setTipoCuenta(cuenta.getTipoCuenta());
				cuentas.setEstado(cuenta.getEstado());
				cuentasUpdate = cuentaRepository.save(cuentas);		
			}
				
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			
		}		
		return cuentasUpdate;
		
	}

	@Override
	public Cuentas saveCuentas(Cuentas cuenta) {
		// TODO Auto-generated method stub
		Cuentas cuentasInsert = null;
		try {
			Cuentas cuentas = cuentaRepository.findBynoCuenta(cuenta.getNoCuenta());
			logger.error("Ha ocurrido un error: "+ cuenta.getCliente().getCid());
			Clientes cliente = clienteRepository.findById(cuenta.getCliente().getCid());
			logger.error("Ha ocurrido un error: "+ cliente.getEstado());
			if(cuentas==null) {
				if(cliente !=null) {					
					cuenta.setCliente(cliente);
					cuentasInsert = cuentaRepository.save(cuenta);	
				}
				
			}	
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
			
		}		
		return cuentasInsert;
	}

	@Override
	public Cuentas findbyId(int id) {
		// TODO Auto-generated method stub
		Cuentas cuenta = new Cuentas();
		try {
			cuenta = cuentaRepository.findById(id);
		}
		catch(Exception exe) {
			logger.error("Ha ocurrido un error: "+ exe.getMessage());
		}		
		return cuenta;
	}

	
}
