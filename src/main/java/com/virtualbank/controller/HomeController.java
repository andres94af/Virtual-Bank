package com.virtualbank.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		session.setAttribute("idusuario", null);
		model.addAttribute("sesion", "LOGUEADO");
		model.addAttribute("titulo", "Hola desde home");
		return "home";
	}
	
	@GetMapping("/cuentas")
	private String verTiposDeCuentas(Model model) {
		model.addAttribute("titulo", "Tipos de cuenta");
		return "datosPersonales";
	}
	
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
	
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession session) {
		if(session.getAttribute("idusuario") != null) {
			return "redirect:/";
		}
		model.addAttribute("titulo", "Inicie sesión en su cuenta");
		return "login";
	}
	
}
