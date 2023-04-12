package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

public interface IMovimientosService {

	Movimientos save(Movimientos movimiento);
	List<Movimientos> findAll();
	List<Movimientos> findByUsuario(Usuario usuario);
	double obtenerIngresoMensual(Usuario usuario);
	double obtenerEgresoMensual(Usuario usuario);
	void generarTransferencia(Optional<Usuario> usuario1, Optional<Usuario> usuario2, double monto, String ctaDestino, double interesEnvia);
	void generarExtraccion(Optional<Usuario> usuario, double dinero);
	void generarDeposito(Usuario usuario, double dinero);
}
