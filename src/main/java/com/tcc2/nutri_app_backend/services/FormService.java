package com.tcc2.nutri_app_backend.services;

import com.tcc2.nutri_app_backend.entities.DTOs.QuestionAnswerDTO;
import com.tcc2.nutri_app_backend.entities.Form;
import com.tcc2.nutri_app_backend.entities.Patient;
import com.tcc2.nutri_app_backend.entities.Question;
import com.tcc2.nutri_app_backend.repositories.FormRepository;
import com.tcc2.nutri_app_backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private PatientService patientService;

    public void createForm(UUID patientId) {
        Patient patient = patientService.getPatientById(patientId);

        Form form = new Form();
        form.setPatient(patient);

        formRepository.save(form);
    }

    public Form findById(UUID formId) {
        Form form = formRepository.findById(formId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        return form;
    }

    public void addQuestion(QuestionAnswerDTO data, UUID formId) {
        Form form = findById(formId);
        List<Question> questions = form.getQuestions();

        Question question = new Question();
        question.setQuestion(data.question());
        questions.add(question);

        formRepository.save(form);
    }

    public List<Question> findAllQuestions(UUID patientId) {
        Patient patient = patientService.getPatientById(patientId);
        UUID formId = patient.getForm().getId();
        Form form = findById(formId);
        List<Question> questions = form.getQuestions();

        questions.sort(Comparator.comparing(Question::getCreatedAt));

        return questions;
    }

    public Question findQuestionById(UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        return question;
    }

    public void update(QuestionAnswerDTO data, UUID questionId) {
        Question question = findQuestionById(questionId);

        if (data.question() != null) {
            question.setQuestion(data.question());
        }

        if (data.answer() != null) {
            question.setAnswer(data.answer());
        }

        questionRepository.save(question);
    }

    public void updateAnswers(List<QuestionAnswerDTO> data) {
        for(QuestionAnswerDTO dto : data) {
            UUID questionId = dto.questionId();
            Question question = findQuestionById(questionId);

            if (dto.question() != null) {
                question.setQuestion(dto.question());
            }

            if (dto.answer() != null) {
                question.setAnswer(dto.answer());
            }

            questionRepository.save(question);
        }
    }

    public void deleteQuestion(UUID questionId) {
        Question question = findQuestionById(questionId);
        questionRepository.delete(question);
    }
}
