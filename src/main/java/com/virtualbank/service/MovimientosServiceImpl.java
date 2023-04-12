package com.virtualbank.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;
import com.virtualbank.repository.IMovimientosRepository;

@Service
public class MovimientosServiceImpl implements IMovimientosService {

	@Autowired
	private IMovimientosRepository movimientoRepo; 

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public List<Movimientos> findAll() {
		return movimientoRepo.findAll();
	}

	@Override
	public List<Movimientos> findByUsuario(Usuario usuario) {
		List<Movimientos> movimientosPorUsuario = movimientoRepo.findByUsuario(usuario);
		Collections.sort(movimientosPorUsuario, new Comparator<Movimientos>() {
			public int compare(Movimientos m1, Movimientos m2) {
				return m2.getId().compareTo(m1.getId());
			}
		});
		return movimientosPorUsuario;
	}

	@Override
	public Movimientos save(Movimientos movimiento) {
		return movimientoRepo.save(movimiento);
	}

	@Override
	public double obtenerIngresoMensual(Usuario usuario) {
		double totalMensual = 0;
		Calendar cal = Calendar.getInstance();
		int mesActual = cal.get(Calendar.MONTH) + 1;
		int añoActual = cal.get(Calendar.YEAR);
		List<Movimientos> movimientos = findByUsuario(usuario);
		if (!movimientos.isEmpty()) {
			List<Movimientos> movimientosIgresos = new ArrayList<Movimientos>();
			for (Movimientos m : movimientos) {
				if (m.getTipo().equals("I")
						&& (m.getFecha().getMonth() + 1 == mesActual && m.getFecha().getYear() + 1900 == añoActual)) {
					movimientosIgresos.add(m);
				}
			}
			totalMensual = movimientosIgresos.stream().mapToDouble(m -> m.getMonto()).sum();
			return totalMensual;
		} else {
			return totalMensual;
		}
	}

	@Override
	public double obtenerEgresoMensual(Usuario usuario) {
		double totalMensual = 0;
		Calendar cal = Calendar.getInstance();
		int mesActual = cal.get(Calendar.MONTH) + 1;
		int añoActual = cal.get(Calendar.YEAR);
		List<Movimientos> movimientos = findByUsuario(usuario);
		if (!movimientos.isEmpty()) {
			List<Movimientos> movimientosEgresos = new ArrayList<Movimientos>();
			for (Movimientos m : movimientos) {
				if (m.getTipo().equals("E")
						&& (m.getFecha().getMonth() + 1 == mesActual && m.getFecha().getYear() + 1900 == añoActual)) {
					movimientosEgresos.add(m);
				}
			}
			totalMensual = movimientosEgresos.stream().mapToDouble(m -> m.getMonto()).sum();
			return totalMensual;
		} else {
			return totalMensual;
		}
	}

	@Override
	public void generarTransferencia(Optional<Usuario> usuario1, Optional<Usuario> usuario2, double monto,
			String ctaDestino, double interesEnvia) {
		Usuario usuarioEnvia = usuario1.get();
		Usuario usuarioRecibe = usuario2.get();
		Movimientos movimientoEnvia = new Movimientos("E", new Date(), monto, interesEnvia, ctaDestino, "Transferencia",
				usuarioEnvia);
		Movimientos movimientoRecibe = new Movimientos("I", new Date(), monto, 0, ctaDestino, "Transferencia",
				usuarioRecibe);
		double nuevoSaldoEnvia = Math.round((usuarioEnvia.getSaldo() - monto - interesEnvia) * 100.0) / 100.0;
		double nuevoSaldoRecibe = Math.round((usuarioRecibe.getSaldo() + monto) * 100.0) / 100.0;
		usuarioEnvia.setSaldo(nuevoSaldoEnvia);
		usuarioRecibe.setSaldo(nuevoSaldoRecibe);
		usuarioService.save(usuarioEnvia);
		save(movimientoEnvia);
		usuarioService.save(usuarioRecibe);
		save(movimientoRecibe);
	}

	@Override
	public void generarExtraccion(Optional<Usuario> usuario, double dinero) {
		Usuario usuario1 = usuario.get();
		double interes = (dinero / 100) * usuario1.getInteres();
		Movimientos movimiento = new Movimientos("E", new Date(), dinero, interes, "Retira en cajero", "Extracción",
				usuario1);
		double nuevoSaldo = Math.round((usuario1.getSaldo() - dinero - interes) * 100.0) / 100.0;
		usuario1.setSaldo(nuevoSaldo);
		usuarioService.save(usuario1);
		save(movimiento);
	}

	@Override
	public void generarDeposito(Usuario usuario, double dinero) {
		double interes = (dinero / 100) * usuario.getInteres();
		Movimientos movimiento = new Movimientos("I", new Date(), dinero, interes, usuario.getNumeroCuenta(), "Depósito",
				usuario);
		double nuevoSaldo = Math.round((usuario.getSaldo() + dinero - interes) * 100.0) / 100.0;
		usuario.setSaldo(nuevoSaldo);
		usuarioService.save(usuario);
		save(movimiento);
	}

}
