package com.virtualbank;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VirtualBankApplicationTests {

	@Test
	void contextLoads() {
		Date date = new Date();
		String dia = new SimpleDateFormat("dd/MM/yyyy").format(date);
		String hora = new SimpleDateFormat("HH:mm").format(date);
		System.out.println(dia);
		System.out.println(hora);
	}

}
