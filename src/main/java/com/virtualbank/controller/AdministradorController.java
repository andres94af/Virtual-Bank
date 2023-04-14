package com.virtualbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.virtualbank.model.Usuario;
import com.virtualbank.service.IMovimientosService;
import com.virtualbank.service.IRegistroIngresoService;
import com.virtualbank.service.IUsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

	@Autowired
	private IRegistroIngresoService registroIngresoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IMovimientosService movimientosService;

//METODO QUE REDIRECCIONA A LA VISTA PRINCIPAL DEL ADMINISTRADOR
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		Optional<Usuario> usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		model.addAttribute("ultimoIngreso", registroIngresoService.findLast(usuario.get()));
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("titulo", "ADMINISTRADOR");
		return "administrador/home_administrador";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE LISTADO DE CLIENTES
	@GetMapping("/clientes")
	public String listadoClientes(Model model, HttpSession session) {
		model.addAttribute("titulo", "LISTADO DE CLIENTES");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		List<Usuario> listaDeClientes = usuarioService.findAll();
		listaDeClientes.removeIf(c -> c.getRol().equals("ADMIN"));
		model.addAttribute("clientes", listaDeClientes);
		return "administrador/listado_clientes";
	}

//METODO QUE FILTRA EL LISTADO DE CLIENTES LISTADO DE CLIENTES
	@PostMapping("/clientes/filtrar")
	public String listadoClientesFiltrado(Model model, HttpSession session, @RequestParam String filtro,
			@RequestParam String valor) {
		model.addAttribute("titulo", "LISTADO DE CLIENTES");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		List<Usuario> listaClientes = usuarioService.findAll();
		listaClientes.removeIf(c -> c.getRol().equals("ADMIN"));
		String valorMinuscula = valor.toLowerCase();
		switch (filtro) {
		case "nombre":
			listaClientes = listaClientes.stream().filter(c -> c.getNombre().toLowerCase().contains(valorMinuscula))
					.collect(Collectors.toList());
			break;
		case "apellido":
			listaClientes = listaClientes.stream().filter(c -> c.getApellido().toLowerCase().contains(valorMinuscula))
			.collect(Collectors.toList());
			break;
		case "dni":
			listaClientes = listaClientes.stream().filter(c -> c.getDni().toLowerCase().contains(valorMinuscula))
			.collect(Collectors.toList());
			break;
		case "cuenta":
			listaClientes = listaClientes.stream().filter(c -> c.getNumeroCuenta().toLowerCase().contains(valorMinuscula))
			.collect(Collectors.toList());
			break;
		default:
			return "redirect:/administrador/clientes";
		}
		model.addAttribute("clientes", listaClientes);
		return "administrador/listado_clientes";
	}

//METODO QUE ACTIVA/INACTIVA CLIENTES
	@GetMapping("/clientes/activo/{id}")
	public String activarInactivarClientes(@PathVariable Integer id) {
		Optional<Usuario> clienteOpt = usuarioService.findById(id);
		if (clienteOpt.isPresent()) {
			Usuario cliente = clienteOpt.get();
			if (cliente.isActivo()) {
				cliente.setActivo(false);
			} else {
				cliente.setActivo(true);
			}
			usuarioService.save(cliente);
			return "redirect:/administrador/clientes?e_ok";
		} else {
			return "redirect:/administrador/clientes?e_error";
		}
	}

//METODO QUE REDIRECCIONA A LA VISTA DE DETAES DE CLIENTES
	@GetMapping("/clientes/detalle/{id}")
	public String verDetalleCliente(Model model, HttpSession session, @PathVariable Integer id) {
		Optional<Usuario> clienteOpt = usuarioService.findById(id);
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("titulo", "Información detallada del cliente");
		if (!clienteOpt.isEmpty()) {
			Usuario cliente = clienteOpt.get();
			model.addAttribute("cliente", cliente);
		} else {
			return "redirect:/administrador/clientes";
		}
		return "administrador/detalle_clientes";
	}

//METODO QUE REDIRECCIONA A LA VISTA DE METRICAS
	@GetMapping("/metricas")
	public String metricas(Model model, HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		model.addAttribute("titulo", "MÉTRICAS");
		model.addAttribute("ingrPromMesAnoAct", movimientosService.obtenerIngresoMensualPromedio());
		model.addAttribute("egrPromMesAnoAct", movimientosService.obtenerEgresoMensualPromedio());
		model.addAttribute("promGanXTrans", movimientosService.promedioDeGananciaPorTransaccion());
		model.addAttribute("cliRegistrados", usuarioService.clientesRegistrados());
		model.addAttribute("cliActivos", usuarioService.numeroClientesActivosInactivos("activo"));
		model.addAttribute("cliInactivos", usuarioService.numeroClientesActivosInactivos("inactivo"));
		model.addAttribute("cliXdia", registroIngresoService.ingresoClientesPorDia());
		return "administrador/metricas";
	}

}
