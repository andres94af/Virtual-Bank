package com.virtualbank.service;

import java.util.List;

import com.virtualbank.model.RegistroIngreso;
import com.virtualbank.model.Usuario;

public interface IRegistroIngresoService {
	
	List<RegistroIngreso> findAll();
	List<RegistroIngreso> findByUsuario(Usuario usuario);
	
}
