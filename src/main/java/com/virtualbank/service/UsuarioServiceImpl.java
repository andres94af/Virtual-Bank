package com.virtualbank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Usuario;
import com.virtualbank.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	@Override
	public List<Usuario> findAll() {
		return usuarioRepo.findAll();
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepo.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {
		return usuarioRepo.findByUsername(username);
	}

	@Override
	public String generarNumeroCuenta() {
		int ultimoId = 0;
		String comienzo = "ES12";
		List<Usuario> usuarios = findAll();
		List<Integer> idUsuarios = new ArrayList<Integer>();
		usuarios.stream().forEach(u -> idUsuarios.add(u.getId()));
		if (idUsuarios.isEmpty()) {
			ultimoId = 1;
		}else {
			ultimoId = idUsuarios.stream().max(Integer::compare).get();
			ultimoId++;
		}
		Random random = new Random();
    int random1 = random.nextInt(8999) + 1000;
    int random2 = random.nextInt(8999) + 1000;
		String numeroCuenta = "";
		if(ultimoId < 10) {
			numeroCuenta = comienzo + " 000" + ultimoId + " " + random1 + " " + random2;
		}else if(ultimoId < 100) {
			numeroCuenta = comienzo + " 00" + ultimoId + " " + random1 + " " + random2;
		}else if(ultimoId < 1000) {
			numeroCuenta = comienzo + " 0" + ultimoId + " " + random1 + " " + random2;
		}else{
			numeroCuenta = comienzo + " " + ultimoId + " " + random1 + " " + random2;
		}
		System.out.println("El numero de cuenta generado es: " + numeroCuenta);
		return numeroCuenta;
	}

}
