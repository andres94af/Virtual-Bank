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
	
	
	public void enviarMailContacto(String nombre, String email, String telefono, String asunto, String mensaje) {
		String mensajeCompleto = "<p>Nombre de contacto: " + nombre + " </p>"
													+ "<p>Telefono de contacto: " + telefono + " </p>"
													+ "<p>Email de contacto: " + email + " </p>"
													+ "<p>Mensaje: " + mensaje + " </p>";
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
}