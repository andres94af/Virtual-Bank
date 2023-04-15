package com.virtualbank;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.virtualbank.service.IMovimientosService;
import com.virtualbank.service.IRegistroIngresoService;
import com.virtualbank.service.IUsuarioService;

@SpringBootTest
class VirtualBankApplicationTests {

	@Autowired
	IRegistroIngresoService ingresoService;

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IMovimientosService movimientosService;

	@Test
	void contextLoads() {	
		LocalDate dia = LocalDate.now();
		System.out.println(dia.toString());
		System.out.println("Hoy es el dia :" + dia.getDayOfMonth());
	}
}
