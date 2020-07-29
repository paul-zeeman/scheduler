package com.pzeeman.scheduler.services;

import com.pzeeman.scheduler.models.Appointment;
import com.pzeeman.scheduler.repositories.AppointmentRepository;
import com.pzeeman.scheduler.services.utils.AlertPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AlertPatient patientAlerter;

    public List<Appointment> list() {
        return appointmentRepository.findAll();
    }

    static final int ONE_MINUTE_IN_MILLIS = 60000;

    public void createAppointment(Long patientId,
                                  String appointmentStartTime) throws ParseException {

        Appointment newAppointment = new Appointment();
        String appointmentHour = appointmentStartTime.substring(0, appointmentStartTime.indexOf(':'));
        String appointmentMinute = appointmentStartTime.substring(appointmentStartTime.indexOf(':') + 1, appointmentStartTime.length());

        Calendar appointment = Calendar.getInstance();
        appointment.setTime(new Date(System.currentTimeMillis()));
        appointment.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        appointment.set(Calendar.HOUR, Integer.valueOf(appointmentHour));
        appointment.set(Calendar.MINUTE, Integer.valueOf(appointmentMinute));
        appointment.set(Calendar.SECOND, 0);
        appointment.set(Calendar.MILLISECOND,0);

        Date expectedEndTime = new Date(appointment.getTimeInMillis() + (30 * ONE_MINUTE_IN_MILLIS));
        newAppointment.setPatientId(patientId);
        newAppointment.setStartTime(appointment.getTime());
        newAppointment.setExpectedEndTime(expectedEndTime);

        appointmentRepository.saveAndFlush(newAppointment);
    }

    public void setAppointmentEndTime(Long appointmentId, String endTime) {

        Appointment finishedAppointment = appointmentRepository.getOne(appointmentId);

        String endHour = endTime.substring(0, endTime.indexOf(':'));
        String endMinute = endTime.substring(endTime.indexOf(':') + 1, endTime.length());

        Calendar appointmentEnd = Calendar.getInstance();
        appointmentEnd.setTime(new Date(System.currentTimeMillis()));
        appointmentEnd.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        appointmentEnd.set(Calendar.HOUR, Integer.valueOf(endHour));
        appointmentEnd.set(Calendar.MINUTE, Integer.valueOf(endMinute));
        appointmentEnd.set(Calendar.SECOND, 0);
        appointmentEnd.set(Calendar.MILLISECOND,0);
        Date actualEndTime = appointmentEnd.getTime();

        finishedAppointment.setActualEndTime(actualEndTime);
        appointmentRepository.saveAndFlush(finishedAppointment);

        Date expectedEndTime = finishedAppointment.getExpectedEndTime();
        Long offset = actualEndTime.getTime() - expectedEndTime.getTime();
        if (offset > 0) {
            updateLaterAppointments(finishedAppointment.getId(), actualEndTime.getTime(), offset);
        }
    }

    private void updateLaterAppointments(Long finishedAppointmentId, Long appointmentsAfter, Long offset) {
        List<Appointment> allAppointments = appointmentRepository.findAll();

        Long nextStartTime = appointmentsAfter;

        for (Appointment appointment : allAppointments) {
            Long oldStartTime = appointment.getStartTime().getTime();
            if ( appointment.getId() != finishedAppointmentId
                    && (appointment.getStartTime().getTime() < nextStartTime)
                    && (appointment.getActualEndTime() == null)) {
                Long newStartTime = oldStartTime + offset;
                appointment.setStartTime(new Date(newStartTime));
                appointment.setExpectedEndTime(new Date(newStartTime + (30 * ONE_MINUTE_IN_MILLIS)));
                appointmentRepository.saveAndFlush(appointment);
                patientAlerter.alert(appointment.getPatientId(), appointment.getStartTime());
                nextStartTime = appointment.getExpectedEndTime().getTime();
            }
        }
    }
}
