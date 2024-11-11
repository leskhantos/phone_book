package com.example.phone_book.controller;

import com.example.phone_book.repository.ContactRepository;
import com.example.phone_book.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final ContactRepository contactRepository;
    private final ReportService reportService;

    public ReportController(ContactRepository contactRepository, ReportService reportService) {
        this.contactRepository = contactRepository;
        this.reportService = reportService;
    }

    @GetMapping("/contacts/pdf")
    public ResponseEntity<InputStreamResource> getContactsPdf() {
        ByteArrayInputStream pdf = reportService.generateContactsPdf(contactRepository.findAll());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=contact-list.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}