# scheduler
A scheduling system with notification for changes

### Some Known Inefficencies
- This application is using an in-memory H2 database that is rebuilt and reseeded on every restart of the application
- This application only deals with the current date.  All appointments when made or changed are assumed to be for today
    * Therefore, the date is set with System.currentTimeMillis() - making it susceptible to the time of the host platform.
- The code in AppointmentService to create a date for the appointment is duplicated in two spots and could be refactored into a reusable method.
- The code is written in such as way that the appointments are considered to be in chronological order, even when ordered by id

All appointments are expected to be 30 minutes long.  This is not customizable
### Example Endpoints 
- http://localhost:8080/patient?id=1
- GET http://localhost:8080/appointment
- POST http://localhost:8080/appointment/patientId=1&startTime=15:00
- PATCH http://localhost:8080/appointment/appointmentId=1&endTime=16:00
