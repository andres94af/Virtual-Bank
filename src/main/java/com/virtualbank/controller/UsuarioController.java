package com.virtualbank.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
@GetMapping("/registro/c-basica")
private String vistaRegistroCBasica(Model model) {
	model.addAttribute("tipoCuenta", "Cuenta Basica");
	model.addAttribute("titulo", "Registro de clientes");
	return "registro";
}

@GetMapping("/registro/c-joven")
private String vistaRegistroCJoven(Model model) {
	model.addAttribute("tipoCuenta", "Cuenta Joven");
	model.addAttribute("titulo", "Registro de clientes");
	return "registro";
}

@GetMapping("/registrarUsuario")
private String registrarUsuario(Usuario usuario) {
	usuario.setPassword(encoder.encode(usuario.getPassword()));
	usuario.setActivo(true);
	usuario.setLimite(500);
	usuario.setNumeroCuenta(usuarioService.generarNumeroCuenta());
	usuario.setRol("USER");
	usuario.setSaldo(0);
	usuarioService.save(usuario);
	System.out.println("El usuario guardado fue: " + usuario);
	return "redirect:/login";
}

@GetMapping("/acceder")
public String iniciarSesion(Usuario usuario, HttpSession session, Model model) {
	Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
	if (user.isPresent()) {
		session.setAttribute("idusuario", user.get().getId());
		if (user.get().getRol().equals("ADMIN")) {
			model.addAttribute("rol", "ADMIN");
			return "redirect:/";
		}else {
			model.addAttribute("rol", "USER");
			return "redirect:/";
		}
	}
	return "redirect:/";
}

}
