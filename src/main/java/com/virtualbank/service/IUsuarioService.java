package com.virtualbank.service;

import java.util.List;
import java.util.Optional;

import com.virtualbank.model.Usuario;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	String generarNumeroCuenta();
	Optional<Usuario> findByNumeroCuenta(String numeroCuenta);
	int numeroClientesActivosInactivos(String valor);
	int clientesRegistrados();
}
