package com.virtualbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbank.model.RegistroIngreso;
import com.virtualbank.model.Usuario;
import com.virtualbank.repository.IRegistroIngresoRepository;

@Service
public class RegistroIngresoServiceImpl implements IRegistroIngresoService{

	@Autowired
	private IRegistroIngresoRepository registroRepo;
	
	@Override
	public List<RegistroIngreso> findAll() {
		return registroRepo.findAll();
	}

	@Override
	public List<RegistroIngreso> findByUsuario(Usuario usuario) {
		return registroRepo.findByUsuario(usuario);
	}

}
