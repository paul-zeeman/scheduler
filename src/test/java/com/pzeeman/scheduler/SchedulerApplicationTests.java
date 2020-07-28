package com.pzeeman.scheduler;

import com.pzeeman.scheduler.models.Patient;
import com.pzeeman.scheduler.services.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class SchedulerApplicationTests {

	@Autowired
	private PatientService patientService;

	@Test
	public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
		List<Patient> patients = patientService.list();

		Assert.assertEquals(3, patients.size());
	}

}
