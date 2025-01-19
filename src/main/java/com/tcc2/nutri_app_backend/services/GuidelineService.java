package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.GuidelineDTO;
import com.tcc2.nutri_app_backend.entities.Guideline;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.repositories.GuidelineRepoistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class GuidelineService {
    @Autowired
    private GuidelineRepoistory guidelineRepoistory;
    @Autowired
    private PatientService patientService;

    public void createGuideline(GuidelineDTO dto) {
        Patient patient = patientService.getPatientById(dto.patientId());

        Guideline guideline = new Guideline();
        guideline.setTitle(dto.title());
        guideline.setContent(dto.content());
        guideline.setPatient(patient);

        guidelineRepoistory.save(guideline);
    }

    public List<Guideline> getAllGuidelinesByPatient(UUID patientID) {
        List<Guideline> guidelines = guidelineRepoistory.findAllByPatientId(patientID);

        return guidelines;
    }

    public Guideline getGuidelineById(UUID id) {
        Guideline guideline = guidelineRepoistory.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found"));

        return guideline;
    }

    public void editGuideline(UUID id, GuidelineDTO dto) {
        Guideline guideline = getGuidelineById(id);
        guideline.setTitle(dto.title());
        guideline.setContent(dto.content());

        guidelineRepoistory.save(guideline);
    }

    public void deleteGuideline(UUID id) {
        guidelineRepoistory.deleteById(id);
    }
}
