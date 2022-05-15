package com.example.demo.CoreLogic.Interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.BusinessLogic.DTO.ListaMov;
import com.example.demo.CoreLogic.Entities.Movimientos;

@Repository
public interface IMovimientosRepository extends JpaRepository<Movimientos, Integer> { 

	public Movimientos findBynoCuenta(String noCuenta);
	
	public Movimientos findById(int id);
	
	public List<Movimientos> findByFecha(LocalDate fecha);
	
//	@Query("SELECT c.id, c.rolfk , c.menufk, r.nombre, m.nombre FROM RolesMenus c JOIN c.rolfk r JOIN c.menufk m where c.rolfk = (?1)")	 where c.fecha>=?1 and c.fecha<=?2 and  m.cid = ?3"
	@Query("SELECT new com.example.demo.BusinessLogic.DTO.ListaMov( c.fecha, c.saldo , case when c.tipoMov=2 then  -c.valor else c.valor end , c.noCuenta, r.saldoInicial , case when r.tipoCuenta = 1 then 'AHORROS' when r.tipoCuenta=2 then 'Corriente' else '' end, p.nombre) FROM Movimientos c JOIN c.ctas r JOIN r.cliente m JOIN m.persona p  where c.fecha>=?1 and c.fecha<=?2 and  m.cid = ?3")	
	public List<ListaMov> generateEstadoCuenta (LocalDate Fechaini, LocalDate Fechafin, int idCliente);
	
}
