package com.virtualbank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;
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

	}
}
