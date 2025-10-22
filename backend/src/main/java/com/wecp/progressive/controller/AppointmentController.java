package com.wecp.progressive.controller;

import com.wecp.progressive.entity.Appointment;
import com.wecp.progressive.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointment), HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Void> updateAppointment(@PathVariable Integer appointmentId, @RequestBody Appointment appointment) {
        if(appointmentId == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        appointment.setAppointmentId(appointmentId);
        Appointment app = appointmentService.getAppointmentById(appointmentId);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int appointmentId) {
        Appointment app = appointmentService.getAppointmentById(appointmentId);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<Appointment>> getAppointmentByClinic(@PathVariable int clinicId) {
        List<Appointment> app = appointmentService.getAppointmentByClinic(clinicId);
        System.out.println(app);
        if (app == null) {
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatient(@PathVariable int patientId) {
        List<Appointment> app = appointmentService.getAppointmentByPatient(patientId);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentByStatus(@PathVariable String status) {
        List<Appointment> app = appointmentService.getAppointmentByStatus(status);
        if (app == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(app, HttpStatus.OK);
    }
}
