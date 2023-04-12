package com.virtualbank.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;
import com.virtualbank.service.IMovimientosService;
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

	@Autowired
	private IMovimientosService movimientosService;

//METODO QUE REDIRECCIONA A LA VISTA HOME
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		model.addAttribute("titulo", "Hola desde home");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
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
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		model.addAttribute("titulo", "Mis datos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("usuario", usuario.get());
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
		model.addAttribute("ingresoMensual", movimientosService.obtenerIngresoMensual(usuario.get()));
		model.addAttribute("egresoMensual", movimientosService.obtenerEgresoMensual(usuario.get()));
		List<Movimientos> movimientos = movimientosService.findByUsuario(usuario.get());
		if (movimientos.isEmpty()) {
			model.addAttribute("movimientos", null);
		} else if (movimientos.size() < 5) {
			int cantFilas = movimientos.size();
			model.addAttribute("movimientos", movimientosService.findByUsuario(usuario.get()).subList(0, cantFilas));
		} else {
			model.addAttribute("movimientos", movimientosService.findByUsuario(usuario.get()).subList(0, 5));
		}
		return "cliente/home_cliente";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE MOVIMIENTOS HISTORICOS DE CLIENTE
	@GetMapping("/cliente/movimientos")
	private String verMovimientos(Model model, HttpSession session) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		model.addAttribute("titulo", "Movimientos históricos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("movimientos", movimientosService.findByUsuario(usuario.get()));
		return "cliente/movimientos";
	}

//METODO QUE FILTRA LOS MOVIMIENTOS DEL CLIENTE
	@GetMapping("/cliente/movimientos/filtrar")
	private String filtrarMovimientos(Model model, HttpSession session) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		model.addAttribute("titulo", "Movimientos históricos");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		List<Movimientos> movimientos = movimientosService.findByUsuario(usuario.get());
		List<Movimientos> movimientosFiltrados = new ArrayList<Movimientos>();

		// aqui se aplica el filtro
		// <------------------------------------------------------

		model.addAttribute("movimientos", movimientosFiltrados);
		return "cliente/movimientos";
	}

//METODO QUE REDIRECCIONA A LA VISTA PARA REALIZAR TRANSFERENCIAS
	@GetMapping("/cliente/transferencias")
	private String verTransferencias(Model model, HttpSession session) {
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Usuario> usuarios = usuarioService.findAll();
		usuarios.remove(usuario);
		model.addAttribute("titulo", "Transferencias");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("usuarioLogueado", usuario);
		double interes = (usuario.getSaldo()/100) * usuario.getInteres();
		model.addAttribute("saldoTransf", (Math.round((usuario.getSaldo() - interes) * 100.0) / 100.0));
		model.addAttribute("usuarios", usuarios);
		return "cliente/transferencias";
	}

//METODO QUE TRANSFIERE DE UNA CUENTA A OTRA Y REDIRECCION A LA VISTA HOME DEL CLIENTE
	@GetMapping("/cliente/transferencias/enviarTransferencia")
	private String enviarTransferencias(HttpSession session, @RequestParam double monto, @RequestParam String ctaDestino) {
		Optional<Usuario> usuario1 = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		Optional<Usuario> usuario2 = usuarioService.findByNumeroCuenta(ctaDestino);
		double interes = (monto / 100) * usuario1.get().getInteres();
		boolean saldoSuficiente = usuario1.get().getSaldo()>monto+interes;
		if (saldoSuficiente) {
			if (usuario1.isPresent() && usuario2.isPresent()) {
				movimientosService.generarTransferencia(usuario1, usuario2, monto, ctaDestino, interes);
				return "redirect:/cliente/home_cliente?t_exito";
			}else {
				return "redirect:/cliente/transferencias?t_error";			
			}
		}
		return "redirect:/cliente/transferencias?t_insuficiente";
	}

//METODO QUE REDIRECCIONA A LA VISTA DEL CAJERO VIRTUAL (ATM)
	@GetMapping("/cliente/cajero")
	private String cajeroVirtual(Model model, HttpSession session) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		double interes = (usuario.get().getSaldo()/100) * usuario.get().getInteres();
		model.addAttribute("saldoTransf", (Math.round((usuario.get().getSaldo() - interes) * 100.0) / 100.0));
		model.addAttribute("usuario", usuario.get());
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "cliente/cajero_virtual";
	}

//METODO QUE EXTRAE EL DINERO DE LA CUENTA Y REDIRECCIONA A LA VISTA DEL CAJERO VIRTUAL (ATM)
	@GetMapping("/cliente/cajero/extraer")
	private String extraerDineroCajero(HttpSession session, @RequestParam double dinero) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		double interes = (dinero / 100) * usuario.get().getInteres();
		boolean saldoSuficiente = usuario.get().getSaldo()>dinero+interes;
		if (saldoSuficiente) {
			movimientosService.generarExtraccion(usuario, dinero);
			return "redirect:/cliente/cajero?e_exito";			
		}
		return "redirect:/cliente/cajero?e_insuficiente";
	}

//METODO QUE INGRESA EL DINERO DE LA CUENTA Y REDIRECCIONA A LA VISTA DEL CAJERO VIRTUAL (ATM)
	@GetMapping("/cliente/cajero/depositar")
	private String depositarDineroCajero(HttpSession session, @RequestParam double billete10,
			@RequestParam double billete20, @RequestParam double billete50, @RequestParam double billete100) {
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		double dineroBilletes10 = billete10 * 10;
		double dineroBilletes20 = billete20 * 20;
		double dineroBilletes50 = billete50 * 50;
		double dineroBilletes100 = billete100 * 100;
		double dinero = dineroBilletes10 + dineroBilletes20 + dineroBilletes50 + dineroBilletes100;
		movimientosService.generarDeposito(usuario, dinero);
		return "redirect:/cliente/cajero?d_exito";
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
			@RequestParam("telefono") String telefono, @RequestParam("asunto") String asunto,
			@RequestParam("mensaje") String mensaje) {
		mailService.enviarMailContacto(nombre, email, telefono, asunto, mensaje);
		return "redirect:/";
	}

//METODO QUE REDIRECCIONA A LA VISTA LOGIN
//SI ESTA LOGUEADO REDIRECCIONA A HOME
	@GetMapping("/login")
	public String loginPage(Model model, HttpSession session) {
		if (session.getAttribute("idusuario") != null) {
			return "redirect:/";
		}
		model.addAttribute("titulo", "Inicia sesión en tu cuenta");
		return "login";
	}

}
