package com.pzeeman.scheduler.controllers;

import com.pzeeman.scheduler.models.Appointment;
import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.repositories.AppointmentRepository;
import com.pzeeman.scheduler.repositories.PatientRepository;
import com.pzeeman.scheduler.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/appointment")
    public List<Appointment> getAppointmentById(@RequestParam(required = false) Long id) {
        List<Appointment> returnAppointments = new ArrayList<>();
        if (id == null)
            returnAppointments = appointmentRepository.findAll();
        else
            returnAppointments.add(appointmentRepository.getOne(id));
        return returnAppointments;
    }

    @PostMapping("/appointment")
    public void makeAppointment(@RequestParam Long patientId,
                                @RequestParam String startTime) {
        try {
            appointmentService.createAppointment(patientId, startTime);
        }
        catch (ParseException pe) {

        }

    }
}
