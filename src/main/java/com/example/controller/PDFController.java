package com.example.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class PDFController extends ActionSupport {
    @Override
    public String execute() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"generated.pdf\"");

        OutputStream out = response.getOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        float[] columnWidths = {200F, 200F};
        Table table = new Table(columnWidths);
        table.addCell(new Cell().add(new Paragraph("Header 1")));
        table.addCell(new Cell().add(new Paragraph("Header 2")));
        table.addCell(new Cell().add(new Paragraph("Row 1 Col 1")));
        table.addCell(new Cell().add(new Paragraph("Row 1 Col 2")));

        document.add(table);
        document.close(); // This also closes the output stream
        return NONE; // Don't forward to any JSP — response is complete
    }
}
