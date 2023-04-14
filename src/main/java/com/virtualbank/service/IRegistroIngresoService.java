package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import com.virtualbank.model.RegistroIngreso;
import com.virtualbank.model.Usuario;

public interface IRegistroIngresoService {
	Optional<RegistroIngreso> findById(Integer id);
	List<RegistroIngreso> findAll();
	List<RegistroIngreso> findByUsuario(Usuario usuario);
	RegistroIngreso save(RegistroIngreso registro);
	RegistroIngreso nuevoRegistro(Usuario usuario);
	RegistroIngreso findLast(Usuario usuario);
	double ingresoClientesPorDia();
}
