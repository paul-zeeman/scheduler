package com.pzeeman.scheduler.services.utils;

import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AlertPatient {


    @Autowired
    PatientRepository patientRepository;

    public void alert(Long patientId, Date newStartTime) {

        Patient alertedPatient = patientRepository.getOne(patientId);
        String patientName = alertedPatient.getName();
        String mobileNumber = alertedPatient.getMobileNumber();
        SimpleDateFormat newTimeFormatter = new SimpleDateFormat("HH:mm");
        String newTime = newTimeFormatter.format(newStartTime);
        StringBuilder alertMessageBuilder = new StringBuilder();
        alertMessageBuilder.append("Hello ");
        alertMessageBuilder.append(patientName);
        alertMessageBuilder.append(",\n");
        alertMessageBuilder.append("Your appointment has been moved start at ");
        alertMessageBuilder.append(newTime);

        System.out.println(mobileNumber + " - " + alertMessageBuilder.toString());
    }
}
