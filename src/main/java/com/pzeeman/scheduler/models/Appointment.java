package com.pzeeman.scheduler.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;
    private Long patientId;
    private Date startTime;
    private Long lengthInMinutes;

    private Date expectedEndTime;
    private Date actualEndTime;
}
