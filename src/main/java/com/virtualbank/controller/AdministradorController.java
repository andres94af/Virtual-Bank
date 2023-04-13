package com.virtualbank.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("titulo", "ADMINISTRADOR");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
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
	
//METODO QUE ACTIVA/INACTIVA CLIENTES
	@GetMapping("/clientes/activo/{id}")
	public String activarInactivarClientes(@PathVariable Integer id) {
		Optional<Usuario> clienteOpt = usuarioService.findById(id);
		if (clienteOpt.isPresent()) {
			Usuario cliente = clienteOpt.get();
			if(cliente.isActivo()) {
				cliente.setActivo(false);
			}else {
				cliente.setActivo(true);
			}
			usuarioService.save(cliente);
			return "redirect:/administrador/clientes?e_ok";
		}else{
			return "redirect:/administrador/clientes?e_error";
		}
	}
	
//METODO QUE REDIRECCIONA A LA VISTA DE METRICAS
	@GetMapping("/metricas")
	public String metricas(Model model, HttpSession session) {
		model.addAttribute("titulo", "MÉTRICAS");
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "administrador/metricas";
	}

}
