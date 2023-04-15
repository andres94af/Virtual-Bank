package com.virtualbank.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Usuario;

@Service
public class MailService {

	private final Properties properties = new Properties();
	private Session session;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	private void inicializarSesion() {
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.port", "587");
		properties.put("mail.smtp.user", username);
		properties.setProperty("mail.smtp.auth", "true");
		session = Session.getDefaultInstance(properties);
	}

	// METODO QUE ENVIA MAIL AL ADMINISTRADOR DE LA WEB CON LA CONSULTA QUE HIZO EL
	// CLIENTE Y SUS DATOS
	public void enviarMailContacto(String nombre, String email, String telefono, String asunto, String mensaje) {
		String mensajeCompleto = "Nombre de contacto: " + nombre + "<br/>Telefono de contacto: " + telefono
				+ "<br/>Email de contacto: " + email + "<br/><br/>Mensaje: " + mensaje;
		inicializarSesion();
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("andres94.af@Gmail.com"));
			message.setSubject(asunto);
			message.setText(mensajeCompleto, "ISO-8859-1", "html");
			Transport t = session.getTransport("smtp");
			t.connect(username, password);
			t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			t.close();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	// METODO QUE ENVIA UN MAIL AUTOMATICO AL MOMENTO DE REGISTRARSE
	public void enviarMailDeRegistro(Usuario usuario) {
		String mensajeCompleto = "Hola " + usuario.getNombre() + " " + usuario.getApellido()
				+ "<br/>Te damos la bienvenida a tu " + usuario.getTipoDeCuenta()
				+ "<br/>Tu numero de cuenta es: " + usuario.getNumeroCuenta()
				+ "<br/>Esperemos que tengas una excelente experiencia =) " 
				+ "<br/><br/>Para mas informacion sobre tu cuenta ingresa "
				+ "<a href='http://localhost:8080/'>aquí</a>"
				+ "<br/>Disfruta de tu cuenta!";
		inicializarSesion();
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
			message.setSubject("Virtual Bank - Bienvenida nuevo cliente");
			message.setText(mensajeCompleto, "ISO-8859-1", "html");
			Transport t = session.getTransport("smtp");
			t.connect(username, password);
			t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			t.close();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}