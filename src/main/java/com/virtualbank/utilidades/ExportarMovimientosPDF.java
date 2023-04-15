package com.virtualbank.utilidades;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.virtualbank.model.Movimientos;
import com.virtualbank.model.Usuario;

public class ExportarMovimientosPDF {

	private List<Movimientos> listaMovimientos;

	public ExportarMovimientosPDF(List<Movimientos> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.DARK_GRAY);
		celda.setPadding(5);
		Font fuente = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fuente.setColor(Color.WHITE);
		celda.setPhrase(new Phrase("Ingreso/Egreso", fuente));
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Fecha", fuente));
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Monto", fuente));
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Retenido", fuente));
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Destinatario", fuente));
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Descripcion", fuente));
		tabla.addCell(celda);
	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Movimientos m : listaMovimientos) {
			tabla.addCell(m.getTipo());
			tabla.addCell(m.getFecha().toString());
			tabla.addCell("€ " + m.getMonto());
			tabla.addCell("-€ " + m.getInteres());
			tabla.addCell(m.getDestino());
			tabla.addCell(m.getDescripcion());
		}
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Usuario usuario = listaMovimientos.get(0).getUsuario();
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		Font fuente = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fuente.setColor(Color.BLACK);
		
		fuente.setSize(10);
		Paragraph fecha = new Paragraph("Fecha: " + LocalDate.now(), fuente);
		fecha.setAlignment(Paragraph.ALIGN_LEFT);
		documento.add(fecha);
		
		String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
		fuente.setSize(10);
		Paragraph nombre = new Paragraph("Nombre: " + nombreCompleto, fuente);
		nombre.setAlignment(Paragraph.ALIGN_LEFT);
		documento.add(nombre);
		
		fuente.setSize(10);
		Paragraph dni = new Paragraph("DNI / NIE: " + usuario.getDni(), fuente);
		dni.setAlignment(Paragraph.ALIGN_LEFT);
		documento.add(dni);

		fuente.setSize(18);
		Paragraph titulo = new Paragraph("Detalle movimientos cuenta: "+ usuario.getNumeroCuenta() , fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(6);
		tabla.setSpacingBefore(20);
		tabla.setWidths(new float[] { 1.8f, 1.8f, 1.8f, 1.8f, 2.8f, 1.8f });
		tabla.setWidthPercentage(110);
		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);
		tabla.setSpacingAfter(15);
		documento.add(tabla);
		
		fuente.setSize(10);
		Paragraph footer = new Paragraph("Virtual Bank © - Andres Mariano Fernández " + LocalDate.now().getYear());
		footer.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(footer);
		
		documento.close();
	}
}
