package com.wecp.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wecp.progressive.entity.Appointment;
import com.wecp.progressive.repository.AppointmentRepository;
import com.wecp.progressive.service.AppointmentService;
@Service
public class AppointmentServiceImpl implements AppointmentService  {

    @Autowired
    private AppointmentRepository apr;

    @Override
    public int createAppointment(Appointment appointment) {
        return apr.save(appointment).getAppointmentId();
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return apr.findAll();
    }

    @Override
    public List<Appointment> getAppointmentByClinic(int clinicId) {
        return apr.findByClinicClinicId(clinicId);
        // return null;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
       return apr.findById(appointmentId).get();
    }

    @Override
    public List<Appointment> getAppointmentByPatient(int patientId) {
        return apr.findByPatientPatientId(patientId);
        // return null;
    }

    @Override
    public List<Appointment> getAppointmentByStatus(String status) {
        return apr.findByStatus(status);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        Appointment ap = apr.findById(appointment.getAppointmentId()).get();

        ap.setAppointmentDate(appointment.getAppointmentDate());
        ap.setClinic(appointment.getClinic());
        ap.setPatient(appointment.getPatient());
        ap.setPurpose(appointment.getPurpose());
        ap.setStatus(appointment.getStatus());

        apr.save(ap);
    }

}