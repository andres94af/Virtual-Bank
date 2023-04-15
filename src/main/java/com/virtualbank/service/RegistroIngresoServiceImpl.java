package com.virtualbank.service;

import java.time.LocalDate;
import java.time.LocalTime;
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
		LocalDate fechaActual = LocalDate.now();
		LocalTime horaActual = LocalTime.now();
		RegistroIngreso registro = new RegistroIngreso(fechaActual, horaActual, usuario);
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

	@Override
	public double ingresoClientesPorDia() {
		List<RegistroIngreso> ingresos = findAll();
		ingresos.removeIf(i->i.getUsuario().getRol().equals("ADMIN"));
		int totalIngresos = ingresos.size();
		int diasIguales = 1;
		for (int i = 1; i < ingresos.size() ; i++) {
			if(ingresos.get(i).getDia().compareTo(ingresos.get(i-1).getDia())!=0) {
				diasIguales++;
			}
		}
		return totalIngresos/diasIguales;
	}

}
