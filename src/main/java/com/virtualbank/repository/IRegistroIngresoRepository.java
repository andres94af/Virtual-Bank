package com.virtualbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualbank.model.RegistroIngreso;
import com.virtualbank.model.Usuario;

@Repository
public interface IRegistroIngresoRepository extends JpaRepository<RegistroIngreso, Integer> {

	List<RegistroIngreso> findByUsuario(Usuario usuario);
}
