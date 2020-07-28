package com.pzeeman.scheduler.controllers;

import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/patient")
    public Patient patient(@RequestParam(value="id") String id) {
        return patientRepository.getOne(Long.getLong(id));
    }
}
