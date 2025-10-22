package com.wecp.progressive.service;

import com.wecp.progressive.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    int createAppointment(Appointment appointment);

    public void updateAppointment(Appointment appointment);

    public Appointment getAppointmentById(int appointmentId);

    public List<Appointment> getAppointmentByClinic(int clinicId);

    public List<Appointment> getAppointmentByPatient(int patientId);

    public List<Appointment> getAppointmentByStatus(String status);

}
