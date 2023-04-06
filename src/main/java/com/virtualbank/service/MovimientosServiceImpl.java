package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;
import com.virtualbank.repository.IMovimientosRepository;

@Service
public class MovimientosServiceImpl implements IMovimientosService{

	@Autowired
	private IMovimientosRepository movimientoRepo;
	
	@Override
	public List<Movimientos> findAll() {
		return movimientoRepo.findAll();
	}

	@Override
	public Optional<Movimientos> findByUsuario(Usuario usuario) {
		return movimientoRepo.findByUsuario(usuario);
	}

}
