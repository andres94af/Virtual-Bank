package com.virtualbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

@Repository
public interface IMovimientosRepository extends JpaRepository<Movimientos, Integer>{
	
	Optional<Movimientos> findByUsuario(Usuario usuario);

}
