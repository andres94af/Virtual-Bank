package com.virtualbank.controller;

import java.time.LocalDate;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.virtualbank.model.Usuario;
import com.virtualbank.service.IRegistroIngresoService;
import com.virtualbank.service.IUsuarioService;
import com.virtualbank.utilidades.MailService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRegistroIngresoService registrarIngresoService;

	@Autowired
	private MailService mailService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//METODO QUE LLEVA AL FORMULARIO PARA CREAR DISTINTOS TIPOS DE CUENTA
	@GetMapping("/registro/{tipo}")
	private String vistaRegistroCBasica(Model model, @PathVariable String tipo) {
		if (tipo.equals("c_basica")) {
			model.addAttribute("tipoDeCuenta", "Cuenta Básica");
		} else if (tipo.equals("c_joven")) {
			model.addAttribute("tipoDeCuenta", "Cuenta Joven");
		} else if (tipo.equals("admin")) {
			model.addAttribute("tipoDeCuenta", "Cuenta Admin");
		} else {
			return "redirect:/";
		}
		model.addAttribute("titulo", "Registro de clientes");
		return "home/registro";
	}

//METODO PARA GUARDAR USUARIO EN LA BBDD, GENERAR EL PRIMER REGISTRO DE INGRESO Y ENVIA MAIL DE REGISTRO
	@GetMapping("/registrarUsuario")
	private String registrarUsuario(Usuario usuario) {
		usuario.setActivo(true);
		usuario.setDni(usuario.getDni().toUpperCase());
		usuario.setFechaRegistro(LocalDate.now());
		if (usuario.getTipoDeCuenta().equals("Cuenta Admin")) {
			usuario.setRol("ADMIN");
			usuario.setTipoDeCuenta("Cuenta Admin");
			usuario.setSaldo(0);
			usuario.setLimite(0);
			usuario.setNumeroCuenta("S/N");
			usuario.setInteres(0);
		} else {
			if (usuarioService.calcularEdad(usuario) < 30) {
				usuario.setTipoDeCuenta("Cuenta Joven");
				usuario.setInteres(0);
			} else {
				usuario.setTipoDeCuenta("Cuenta Básica");
				usuario.setInteres(1.5);
			}
			usuario.setRol("CLI");
			usuario.setSaldo(0);
			usuario.setLimite(500);
			usuario.setNumeroCuenta(usuarioService.generarNumeroCuenta());
		}
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		registrarIngresoService.nuevoRegistro(usuario);
		mailService.enviarMailDeRegistro(usuario);
		return "redirect:/login?c_exito";
	}

//METODO QUE DA EL ATRIBUTO A "idusuario" AL MOMENTO DE INICIAR SESION Y REDIRECCIONA A LA VISTA QUE CORRESPONDE
//SEGUN SI ES CLI O ADMIN
	@GetMapping("/acceder")
	public String iniciarSesion(HttpSession session, Model model) {
		Optional<Usuario> user = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			registrarIngresoService.nuevoRegistro(user.get());
			if (user.get().getRol().equals("ADMIN")) {
				return "redirect:/administrador";
			} else {
				if (user.get().isActivo()) {
					return "redirect:/cliente/home_cliente";
				} else {
					return "redirect:/cliente/home_cliente?inactivo";
				}
			}
		}
		return "redirect:/";
	}

//METODO QUE ACTUALIZA LOS DATOS DEL CLIENTE Y REDIRECCIONA A LA HOME DEL CLIENTE
	@GetMapping("/actualizarUsuario")
	private String actualizarUsuario(Usuario usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuario.setActivo(usuario.isActivo());
		if (usuarioService.calcularEdad(usuario) < 30) {
			usuario.setTipoDeCuenta("Cuenta Joven");
			usuario.setInteres(0);
		} else {
			usuario.setTipoDeCuenta("Cuenta Básica");
			usuario.setInteres(1.5);
		}
		usuarioService.save(usuario);
		return "redirect:/cliente/home_cliente?act";
	}

}
