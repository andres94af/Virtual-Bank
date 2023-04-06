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
		model.addAttribute("sesion", "LOGUEADO");
		model.addAttribute("titulo", "Hola desde home");
		return "home";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("titulo", "Inicie sesión en su cuenta");
		return "login";
	}
	
}
