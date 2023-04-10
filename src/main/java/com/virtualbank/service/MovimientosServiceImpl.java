package com.virtualbank.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
				if (m.getTipo().equals("I") && 
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
				if (m.getTipo().equals("E") && 
						(m.getFecha().getMonth()+1==mesActual && m.getFecha().getYear()+1900==añoActual)) {
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
