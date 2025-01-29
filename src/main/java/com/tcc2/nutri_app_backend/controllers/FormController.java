package com.tcc2.nutri_app_backend.controllers;

import com.tcc2.nutri_app_backend.entities.DTOs.QuestionAnswerDTO;
import com.tcc2.nutri_app_backend.entities.Question;
import com.tcc2.nutri_app_backend.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/forms")
public class FormController {
    @Autowired
    private FormService formService;

    @PostMapping("/patients/{patientId}")
    public ResponseEntity createForm(@PathVariable UUID patientId) {
        formService.createForm(patientId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<Question>> getForm(@PathVariable UUID patientId) {
        List<Question> questions = formService.findAllQuestions(patientId);

        return ResponseEntity.ok(questions);
    }

    @PostMapping("/{formId}/questions")
    public ResponseEntity addQuestion(@PathVariable UUID formId, @RequestBody QuestionAnswerDTO data) {
        formService.addQuestion(data, formId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity updateQuestion(@PathVariable UUID questionId, @RequestBody QuestionAnswerDTO data) {
        formService.update(data, questionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/questions/answers")
    public ResponseEntity updateAnswers(@RequestBody List<QuestionAnswerDTO> data) {
        formService.updateAnswers(data);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable UUID questionId) {
        formService.deleteQuestion(questionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
