package com.virtualbank.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Override
	public RegistroIngreso save(RegistroIngreso registro) {
		return registroRepo.save(registro);
	}

	@Override
	public RegistroIngreso nuevoRegistro(Usuario usuario) {
		Date date = new Date();
		String dia = new SimpleDateFormat("dd/MM/yyyy").format(date);
		String hora = new SimpleDateFormat("HH:mm").format(date);
		RegistroIngreso registro = new RegistroIngreso(dia, hora, usuario);
		save(registro);
		return registro;
	}

	@Override
	public RegistroIngreso findLast(Usuario usuario) {
		List<RegistroIngreso> listaDeIngresos = findByUsuario(usuario);
		Integer cantidadObjetos = listaDeIngresos.size();
		if (cantidadObjetos>1) {
			return listaDeIngresos.get(cantidadObjetos-2);			
		}
		return listaDeIngresos.get(0);
	}

	@Override
	public Optional<RegistroIngreso> findById(Integer id) {
		return registroRepo.findById(id);
	}

}
