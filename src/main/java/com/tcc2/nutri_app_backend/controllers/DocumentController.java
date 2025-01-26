package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DAO.DocumentDAO;
import com.tcc2.nutri_app_backend.entities.Document;
import com.tcc2.nutri_app_backend.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam UUID patientId,
            @RequestParam String filename,
            @RequestParam("file") MultipartFile file) throws IOException {

        byte[] fileData = file.getBytes();
        var authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();

        Document uploadedDocument = documentService.uploadDocument(patientId, username, filename, fileData);

        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedDocument);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable UUID id) {
        Document document = documentService.getDocument(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<DocumentDAO>> getDocumentsByPatient(@PathVariable UUID patientId) {
        List<DocumentDAO> documents = documentService.getDocumentsByPatientId(patientId);
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Document> deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }

}
