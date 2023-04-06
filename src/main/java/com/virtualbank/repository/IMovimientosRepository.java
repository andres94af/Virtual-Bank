package com.virtualbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualbank.model.Movimientos;

@Repository
public interface IMovimientosRepository extends JpaRepository<Movimientos, Integer>{}
