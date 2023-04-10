package com.virtualbank.service;

import java.util.List;
import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

public interface IMovimientosService {

	Movimientos save(Movimientos movimiento);
	List<Movimientos> findAll();
	List<Movimientos> findByUsuario(Usuario usuario);
	double obtenerIngresoMensual(Usuario usuario);
	double obtenerEgresoMensual(Usuario usuario);
	
}
