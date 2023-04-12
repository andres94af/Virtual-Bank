package com.virtualbank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

//METODO QUE REDIRECCIONA A LA VISTA PRINCIPAL DEL ADMINISTRADOR
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		model.addAttribute("titulo", "Hola desde home del ADMINISTRADOR");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "administrador/home_administrador";
	}
	
}
