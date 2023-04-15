package com.virtualbank.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "registro_ingreso")
public class RegistroIngreso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dia;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	@ManyToOne
	private Usuario usuario;
	
	public RegistroIngreso() {}

	public RegistroIngreso(Integer id, LocalDate dia, LocalTime hora, Usuario usuario) {
		this.id = id;
		this.dia = dia;
		this.hora = hora;
		this.usuario = usuario;
	}
	
	public RegistroIngreso(LocalDate dia, LocalTime hora, Usuario usuario) {
		this.dia = dia;
		this.hora = hora;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "RegistroIngreso [dia=" + dia + ", hora=" + hora + ", usuario=" + usuario.getNombre() + "]";
	}
	
}
