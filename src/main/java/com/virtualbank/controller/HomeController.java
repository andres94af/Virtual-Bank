package com.virtualbank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.virtualbank.service.MailService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private MailService mailService;

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		model.addAttribute("titulo", "Hola desde home");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "home";
	}

	@GetMapping("/cuentas")
	private String verTiposDeCuentas(Model model, HttpSession session) {
		model.addAttribute("titulo", "Descubre las cuentas que tenemos para ti..");
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

	@GetMapping("/contacto")
	private String contacto(Model model, HttpSession session) {
		model.addAttribute("titulo", "Contacta con nosotros");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "contacto";
	}

	@PostMapping("/enviarConsulta")
	public String enviarConsultaMail(@RequestParam("nombre") String nombre, @RequestParam("email") String email,
			@RequestParam("telefono") String telefono, @RequestParam("asunto") String asunto, @RequestParam("mensaje") String mensaje) {
		mailService.enviarMailContacto(nombre, email, telefono, asunto, mensaje);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("titulo", "Inicia sesión en tu cuenta");
		return "login";
	}

}
