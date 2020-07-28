package com.pzeeman.scheduler.services;

import com.pzeeman.scheduler.models.Appointment;
import com.pzeeman.scheduler.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> list() {
        return appointmentRepository.findAll();
    }

    public void createAppointment (Long patientId,
                                   String startTime) throws ParseException {

        Appointment newAppointment = new Appointment();

        Calendar today = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = simpleDateFormat.parse(startTime);

        today.setTime(startDate);

        newAppointment.setPatientId(patientId);
        newAppointment.setStartTime(startDate);

        appointmentRepository.saveAndFlush(newAppointment);
    }

}
