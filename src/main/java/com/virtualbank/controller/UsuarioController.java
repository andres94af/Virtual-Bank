package com.virtualbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtualbank.model.Usuario;
import com.virtualbank.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
@GetMapping("/registro")
private String vistaRegistro(Model model) {
	model.addAttribute("titulo", "Registro de clientes");
	return "registro";
}

@GetMapping("/registrarUsuario")
private String registrarUsuario(Usuario usuario) {
	usuario.setActivo(true);
	usuario.setLimite(500);
	usuario.setNumeroCuenta(usuarioService.generarNumeroCuenta());
	usuario.setRol("USER");
	usuario.setSaldo(0);
	usuarioService.save(usuario);
	return "redirect:/login";
}

}
