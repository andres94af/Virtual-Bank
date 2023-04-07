package com.virtualbank.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String apellido;
	
	@NotEmpty
	private String dni;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	@NotEmpty
	private String nacionalidad;

	@NotEmpty
	private String direccion;

	@NotEmpty
	private String telefono;

	@NotEmpty
	@Column(unique = true)
	@Email
	private String email;

	@NotEmpty
	private String genero;

	private String rol;

	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotEmpty
	private String password;

	private String numeroCuenta;

	private double saldo;

	private double limite;

	private boolean activo;

	@OneToMany(mappedBy = "usuario")
	private List<Movimientos> movimientos;
	
	@OneToMany(mappedBy = "usuario")
	private List<RegistroIngreso> registroIngreso;

	public Usuario() {
	}

	public Usuario(Integer id, @NotEmpty String nombre, @NotEmpty String apellido, @NotEmpty String dni,
			@NotNull Date fechaNacimiento, @NotEmpty String nacionalidad, @NotEmpty String direccion,
			@NotEmpty String telefono, @NotEmpty @Email String email, @NotEmpty String genero, String rol,
			@NotEmpty String username, @NotEmpty String password, String numeroCuenta, double saldo, double limite,
			boolean activo, List<Movimientos> movimientos, List<RegistroIngreso> registroIngreso) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.genero = genero;
		this.rol = rol;
		this.username = username;
		this.password = password;
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.limite = limite;
		this.activo = activo;
		this.movimientos = movimientos;
		this.registroIngreso = registroIngreso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Movimientos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimientos> movimientos) {
		this.movimientos = movimientos;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public List<RegistroIngreso> getRegistroIngreso() {
		return registroIngreso;
	}

	public void setRegistroIngreso(List<RegistroIngreso> registroIngreso) {
		this.registroIngreso = registroIngreso;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento="
				+ fechaNacimiento + ", nacionalidad=" + nacionalidad + ", direccion=" + direccion + ", telefono=" + telefono
				+ ", email=" + email + ", genero=" + genero + ", rol=" + rol + ", username=" + username + ", password="
				+ password + ", numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", limite=" + limite + ", activo=" + activo
				+ ", movimientos=" + movimientos + ", registroIngreso=" + registroIngreso + "]";
	}
}
