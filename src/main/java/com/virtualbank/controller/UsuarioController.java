package com.virtualbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@GetMapping("/misDatos")
	private String verDatos(Model model) {
		model.addAttribute("titulo", "Mis datos");
		return "datosPersonales";
	}
	
	@GetMapping("/movimientos")
	private String verMovimientos(Model model) {
		model.addAttribute("titulo", "Mis movimientos");
		return "movimientos";
	}
	
	@GetMapping("/transferencias")
	private String verTransferencias(Model model) {
		model.addAttribute("titulo", "Transferencias");
		return "transferencias";
	}
	
	@GetMapping("/cajero")
	private String cajeroVirtual(Model model) {
		model.addAttribute("titulo", "Cajero virtual");
		return "cajeroVirtual";
	}

}
