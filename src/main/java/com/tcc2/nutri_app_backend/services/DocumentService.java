package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DAO.DocumentDAO;
import com.tcc2.nutri_app_backend.entities.Document;
import com.tcc2.nutri_app_backend.entities.Nutritionist;
import com.tcc2.nutri_app_backend.infra.exceptions.DocumentNotFoundException;
import com.tcc2.nutri_app_backend.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private NutritionistService nutritionistService;

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<DocumentDAO> convertDocumentsToDAO(List<Document> documents) {
        return documents.stream()
                .map(document -> new DocumentDAO(
                        document.getId().toString(),  // Convertendo UUID para String
                        document.getFilename(),
                        document.getPatientId().toString(),  // Convertendo UUID para String
                        document.getNutritionistId().toString(),
                        getDocumentSize(document)
                ))
                .collect(Collectors.toList());
    }

    public long getDocumentSize(Document document) {

        if (document != null) {
            try {
                // Obtendo o BLOB do banco de dados
                byte[] fileBlob = document.getFileData(); // Supondo que você tem o BLOB armazenado na coluna 'fileData'

                // Retornando o tamanho do arquivo (em bytes)
                return fileBlob.length;
            } catch (Exception e) {
                // Tratar erro, caso ocorra
                e.printStackTrace();
            }
        }

        return 0; // Caso o documento não seja encontrado
    }

    public List<DocumentDAO> getDocumentsByPatientId(UUID patientId) {
        List<Document> documents = documentRepository.findAllByPatientId(patientId);
        List<DocumentDAO> documentDAOs = convertDocumentsToDAO(documents);

        return documentDAOs;
    }

    public Document getDocument(UUID id) {
        return documentRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException("Document not found"));
    }

    public void deleteDocument(UUID documentId) {
        documentRepository.deleteById(documentId);
    }

    public Document uploadDocument(UUID patientId, String nutritionistUsername, String filename, byte[] fileData) {
        Nutritionist nutritionist = nutritionistService.getNutritionistByUsername(nutritionistUsername);

        // Criação de um novo documento com os dados fornecidos
        Document document = new Document();
        document.setPatientId(patientId);
        document.setNutritionistId(nutritionist.getId());
        document.setFilename(filename);
        document.setFileData(fileData);

        // Salvando o documento no banco de dados
        return documentRepository.save(document);
    }
}
