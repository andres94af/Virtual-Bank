package com.virtualbank.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;
import com.virtualbank.repository.IMovimientosRepository;

@Service
public class MovimientosServiceImpl implements IMovimientosService{

	@Autowired
	private IMovimientosRepository movimientoRepo;
	
	@Override
	public List<Movimientos> findAll() {
		return movimientoRepo.findAll();
	}

	@Override
	public List<Movimientos> findByUsuario(Usuario usuario) {
		return movimientoRepo.findByUsuario(usuario);
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
				if (m.getTipo().equals("deposito") && 
						(m.getFecha().getMonth()+1==mesActual && m.getFecha().getYear()+1900==añoActual)) {
					movimientosIgresos.add(m);
				}
			}
			totalMensual = movimientosIgresos.stream().mapToDouble(m -> m.getMonto()).sum();
			return totalMensual;
		}else {
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
				if ((m.getTipo().equals("transferencia")||m.getTipo().equals("extraccion")) 
						&& (m.getFecha().getMonth()+1==mesActual && m.getFecha().getYear()+1900==añoActual)) {
					movimientosEgresos.add(m);
				}
			}
			totalMensual = movimientosEgresos.stream().mapToDouble(m -> m.getMonto()).sum();
			return totalMensual;
		}else {
			return totalMensual;
		}
	}

}
