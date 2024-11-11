package com.example.phone_book.service;

import com.example.phone_book.model.Contact;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
@Service
public class ReportService {
    public ByteArrayInputStream generateContactsPdf(List<Contact> contacts) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(out));
            Document document = new Document(pdf);

            document.add(new Paragraph("Phone Book")
                    .setFontSize(16)
                    .setBold());

            Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
            table.addHeaderCell("Name");
            table.addHeaderCell("Surname");
            table.addHeaderCell("Phone");
            table.addHeaderCell("Email");
            table.addHeaderCell("Address");
            table.addHeaderCell("Notes");

            for (Contact contact : contacts) {
                table.addCell(contact.getFirstName());
                table.addCell(contact.getLastName());
                table.addCell(contact.getPhoneNumber());
                table.addCell(contact.getEmail());
                table.addCell(contact.getAddress());
                table.addCell(contact.getNotes());
            }

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error on PDF creation", e);
        }
    }
}