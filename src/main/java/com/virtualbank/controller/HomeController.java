package com.virtualbank.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.virtualbank.model.Usuario;
import com.virtualbank.service.IRegistroIngresoService;
import com.virtualbank.service.IUsuarioService;
import com.virtualbank.service.MailService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private IRegistroIngresoService registroIngresoService;
	
	@Autowired
	private IUsuarioService usuarioService;

//METODO QUE REDIRECCIONA A LA VISTA HOME
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		model.addAttribute("titulo", "Hola desde home");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "home/home";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE TIPOS DE CUENTA VIRTUAL BANK
	@GetMapping("/cuentas")
	private String verTiposDeCuentas(Model model, HttpSession session) {
		model.addAttribute("titulo", "Descubre las cuentas que tenemos para ti..");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "home/cuentas";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE DATOS DEL CLIENTE
	@GetMapping("/cliente/mis_datos")
	private String verDatos(Model model, HttpSession session) {
		model.addAttribute("titulo", "Mis datos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "cliente/datos_personales";
	}
	
//METODO QUE REDIRECCIONA A LA VISTA DE DATOS DEL CLIENTE
	@GetMapping("/cliente/home_cliente")
	private String homeCliente(Model model, HttpSession session) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		model.addAttribute("titulo", usuario.get().getNombre() + ", toda tu información resumida está aquí...");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("ultimoIngreso", registroIngresoService.findLast(usuario.get()));
		model.addAttribute("usuario", usuario.get());
		return "cliente/home_cliente";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE MOVIMIENTOS HISTORICOS DE CLIENTE
	@GetMapping("/cliente/movimientos")
	private String verMovimientos(Model model, HttpSession session) {
		model.addAttribute("titulo", "Mis movimientos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "cliente/movimientos";
	}

//METODO QUE REDIRECCIONA A LA VISTA PARA REALIZAR TRANSFERENCIAS
	@GetMapping("/cliente/transferencias")
	private String verTransferencias(Model model, HttpSession session) {
		model.addAttribute("titulo", "Transferencias");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		System.out.println(session.getAttribute("idusuario"));
		return "cliente/transferencias";
	}

//METODO QUE REDIRECCIONA A LA VISTA DEL CAJERO VIRTUAL (ATM)
	@GetMapping("/cliente/cajero")
	private String cajeroVirtual(Model model, HttpSession session) {
		model.addAttribute("titulo", "Cajero virtual");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "cliente/cajero_virtual";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE CONSULTAS
	@GetMapping("/contacto")
	private String contacto(Model model, HttpSession session) {
		model.addAttribute("titulo", "Contacta con nosotros");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "home/contacto";
	}

//METODO QUE ENVIA POR MAIL LA CONSULTA REALIZADA
	@PostMapping("/enviarConsulta")
	public String enviarConsultaMail(@RequestParam("nombre") String nombre, @RequestParam("email") String email,
			@RequestParam("telefono") String telefono, @RequestParam("asunto") String asunto, @RequestParam("mensaje") String mensaje) {
		mailService.enviarMailContacto(nombre, email, telefono, asunto, mensaje);
		return "redirect:/";
	}
	
//METODO QUE REDIRECCIONA A LA VISTA LOGIN
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("titulo", "Inicia sesión en tu cuenta");
		return "login";
	}

}
