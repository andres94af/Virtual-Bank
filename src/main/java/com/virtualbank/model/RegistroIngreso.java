package com.virtualbank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registro_ingreso")
public class RegistroIngreso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String dia;
	
	private String hora;
	
	@ManyToOne
	private Usuario usuario;
	
	public RegistroIngreso() {}

	public RegistroIngreso(Integer id, String dia, String hora, Usuario usuario) {
		this.id = id;
		this.dia = dia;
		this.hora = hora;
		this.usuario = usuario;
	}
	
	public RegistroIngreso(String dia, String hora, Usuario usuario) {
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

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
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
