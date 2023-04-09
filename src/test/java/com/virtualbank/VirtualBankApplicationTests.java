package com.virtualbank;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.virtualbank.service.IRegistroIngresoService;

@SpringBootTest
class VirtualBankApplicationTests {
	
	@Autowired
	IRegistroIngresoService ingresoService;

	@Test
	void contextLoads() {
		Date date = new Date();
		String dia = new SimpleDateFormat("dd/MM/yyyy").format(date);
		String hora = new SimpleDateFormat("HH:mm").format(date);
		System.out.println(dia);
		System.out.println(hora);
	}

}
