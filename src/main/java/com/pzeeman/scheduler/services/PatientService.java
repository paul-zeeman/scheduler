package com.pzeeman.scheduler.services;

import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> list() {
        return patientRepository.findAll();
    }
}
