package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

public interface IMovimientosService {

	Movimientos save(Movimientos movimiento);
	
	List<Movimientos> findAll();
	
	List<Movimientos> findByUsuario(Usuario usuario);
	
	//Obtiene ingreso mensual por usuario o general
	double obtenerIngresoMensual(Usuario usuario);
	
	//Obtiene el ingreso promedio mensual del año actual
	double obtenerIngresoMensualPromedio();
	
	//Obtiene egreso mensual por usuario
	double obtenerEgresoMensual(Usuario usuario);
	
	//Obtiene el egreso promedio mensual del año actual
	double obtenerEgresoMensualPromedio();
	
	//Obtiene el promedio de ganancias del banco por transaccion
	double promedioDeGananciaPorTransaccion();
	
	void generarTransferencia(Optional<Usuario> usuario1, Optional<Usuario> usuario2, double monto, String ctaDestino, double interesEnvia);
	
	void generarExtraccion(Optional<Usuario> usuario, double dinero);
	
	void generarDeposito(Usuario usuario, double dinero);
}
