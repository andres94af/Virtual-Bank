package com.virtualbank.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "movimientos")
public class Movimientos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String tipo;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	private double monto;
	
	private String destino;
	
	private String descripcion;
	
	@ManyToOne
	private Usuario usuario;
	
	public Movimientos() {}

	public Movimientos(Integer id, String tipo, @NotNull Date fecha, double monto, String destino, String descripcion,
			Usuario usuario) {
		this.id = id;
		this.tipo = tipo;
		this.fecha = fecha;
		this.monto = monto;
		this.destino = destino;
		this.descripcion = descripcion;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Movimientos [id=" + id + ", tipo=" + tipo + ", fecha=" + fecha + ", monto=" + monto + ", destino=" + destino
				+ ", descripcion=" + descripcion + ", usuario=" + usuario.getNombre() + "]";
	}


}
