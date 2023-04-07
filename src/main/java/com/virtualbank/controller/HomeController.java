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
		model.addAttribute("titulo", "Hola desde home");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "home";
	}
	
	@GetMapping("/cuentas")
	private String verTiposDeCuentas(Model model, HttpSession session) {
		model.addAttribute("titulo", "Cuentas de Virtual Bank");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "cuentas";
	}
	
	@GetMapping("/misDatos")
	private String verDatos(Model model, HttpSession session) {
		model.addAttribute("titulo", "Mis datos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "datosPersonales";
	}
	
	@GetMapping("/movimientos")
	private String verMovimientos(Model model, HttpSession session) {
		model.addAttribute("titulo", "Mis movimientos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "movimientos";
	}
	
	@GetMapping("/transferencias")
	private String verTransferencias(Model model, HttpSession session) {
		model.addAttribute("titulo", "Transferencias");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "transferencias";
	}
	
	@GetMapping("/cajero")
	private String cajeroVirtual(Model model, HttpSession session) {
		model.addAttribute("titulo", "Cajero virtual");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "cajeroVirtual";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		return "login";
	}
	
}
