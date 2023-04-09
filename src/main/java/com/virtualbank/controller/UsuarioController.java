package com.virtualbank.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtualbank.model.RegistroIngreso;
import com.virtualbank.model.Usuario;
import com.virtualbank.service.IRegistroIngresoService;
import com.virtualbank.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRegistroIngresoService registrarIngresoService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
//METODO QUE LLEVA AL FORMULARIO PARA CREAR DISTINTOS TIPOS DE CUENTA
@GetMapping("/registro/{tipo}")
private String vistaRegistroCBasica(Model model, @PathVariable String tipo) {
	if (tipo.equals("c_basica")) {
		model.addAttribute("tipoDeCuenta", "Cuenta Básica");
		model.addAttribute("interes", 1.5);
	}else if(tipo.equals("c_joven")) {
		model.addAttribute("tipoDeCuenta", "Cuenta Joven");
		model.addAttribute("interes", 0);
	}else {
		return "redirect:/cuentas";
	}
	model.addAttribute("titulo", "Registro de clientes");
	return "home/registro";
}

//METODO PARA GUARDAR USUARIO EN LA BBDD
//TAMBIEN GENERA PRIMER REGISTRO DE INGRESO
@GetMapping("/registrarUsuario")
private String registrarUsuario(Usuario usuario) {
	usuario.setPassword(encoder.encode(usuario.getPassword()));
	usuario.setActivo(true);
	usuario.setLimite(500);
	usuario.setNumeroCuenta(usuarioService.generarNumeroCuenta());
	usuario.setRol("CLI");
	usuario.setSaldo(0);
	usuarioService.save(usuario);
	System.out.println("El usuario guardado fue: " + usuario);
	RegistroIngreso registro = registrarIngresoService.nuevoRegistro(usuario);
	System.out.println("El registro guardado fue: " + registro);
	return "redirect:/login";
}

//METODO QUE DA EL ATRIBUTO A "idusuario" AL MOMENTO DE INICIAR SESION
@GetMapping("/acceder")
public String iniciarSesion(HttpSession session, Model model) {
	Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
	if (user.isPresent()) {
		session.setAttribute("idusuario", user.get().getId());
		registrarIngresoService.nuevoRegistro(user.get());
		if (user.get().getRol().equals("ADMIN")) {
			model.addAttribute("rol", "ADMIN");
			return "redirect:/";
		}else {
			model.addAttribute("rol", "CLI");
			return "redirect:/";
		}
	}
	return "redirect:/";
}

}
