package com.pzeeman.scheduler.controllers;

import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/patient")
    public Patient getPatientById(@RequestParam Long id) {
        System.out.println("Received request");
        Patient foundPatient = patientRepository.getOne(id);
        System.out.println("name is " + foundPatient.getName());
        return patientRepository.getOne(id);
    }
}
