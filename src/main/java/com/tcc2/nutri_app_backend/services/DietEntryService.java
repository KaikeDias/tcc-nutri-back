package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.DietEntryDTO;
import com.tcc2.nutri_app_backend.entities.DTOs.DietEntryResponseDTO;
import com.tcc2.nutri_app_backend.entities.DietEntry;
import com.tcc2.nutri_app_backend.entities.Meal;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.repositories.DietEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DietEntryService {
    @Autowired
    private DietEntryRepository dietEntryRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MenuService menuService;

    @Transactional
    public DietEntry save(List<String> aliments, LocalDate date, UUID patientId, UUID mealId, MultipartFile photo) throws IOException {
        boolean exists = dietEntryRepository.existsByPatientIdAndMealIdAndDate(patientId, mealId, date);

        if(exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um registro feito nesta data");
        }

        Patient patient = patientService.getPatientById(patientId);
        Meal meal = menuService.getMealById(mealId);

        byte[] photoBytes = null;
        if (photo != null && !photo.isEmpty()) {
            photoBytes = photo.getBytes();
        }

        DietEntry dietEntry = new DietEntry();
        dietEntry.setPatient(patient);
        dietEntry.setTitle(meal.getTitle());
        dietEntry.setMeal(meal);
        dietEntry.setAliments(aliments);
        dietEntry.setDate(date);
        dietEntry.setPhoto(photoBytes);

        dietEntryRepository.save(dietEntry);

        return dietEntry;
    }

    @Transactional
    public Map<LocalDate, List<DietEntryResponseDTO>> getDietEntriesByPatientId(UUID patientId) {
        List<DietEntry> dietEntries = dietEntryRepository.findByPatientId(patientId);

        if (dietEntries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum registro encontrado para o paciente");
        }

        Map<LocalDate, List<DietEntry>> groupedEntries = dietEntries.stream()
                .collect(Collectors.groupingBy(DietEntry::getDate));

        Map<LocalDate, List<DietEntryResponseDTO>> dietEntryDTOs = groupedEntries.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(dietEntry -> new DietEntryResponseDTO(
                                        dietEntry.getId(),
                                        dietEntry.getMeal().getTitle()
                                ))
                                .collect(Collectors.toList())
                ));

        return dietEntryDTOs;
    }

    public DietEntry getDietEntryById(UUID dietEntryId) {
        DietEntry dietEntry = dietEntryRepository.findById(dietEntryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found"));

        return dietEntry;
    }

}
