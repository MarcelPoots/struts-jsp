package com.example.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PDFController extends ActionSupport {

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String execute() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Create table
        float[] columnWidths = {200F, 200F};
        Table table = new Table(columnWidths);
        table.addCell(new Cell().add(new Paragraph("Item")));
        table.addCell(new Cell().add(new Paragraph("Color")));

        // Load XML
        InputStream xmlStream = ServletActionContext.getServletContext()
                .getResourceAsStream("/WEB-INF/classes/data.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document xmlDoc = builder.parse(xmlStream);

        NodeList rows = xmlDoc.getElementsByTagName("row");
        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);
            String col1 = row.getElementsByTagName("col1").item(0).getTextContent();
            String col2 = row.getElementsByTagName("col2").item(0).getTextContent();
            table.addCell(new Cell().add(new Paragraph(col1)));
            table.addCell(new Cell().add(new Paragraph(col2)));
        }

        document.add(table);
        document.close();

        inputStream = new ByteArrayInputStream(baos.toByteArray());
        return SUCCESS;
    }
}
