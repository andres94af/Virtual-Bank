package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

public interface IMovimientosService {

	List<Movimientos> findAll();
	Optional<Movimientos> findByUsuario(Usuario usuario);
	
}
