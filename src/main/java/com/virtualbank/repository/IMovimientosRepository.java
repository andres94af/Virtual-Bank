package com.virtualbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

@Repository
public interface IMovimientosRepository extends JpaRepository<Movimientos, Integer>{
	
	List<Movimientos> findByUsuario(Usuario usuario);

}
