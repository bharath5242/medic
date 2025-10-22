package com.wecp.progressive.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    Clinic clinic;
    
    Date appointmentDate;
    String status;
    String purpose;

    public Appointment() {
    }

    public Appointment(Patient patient, Clinic clinic, Date appointmentDate, String status, String purpose) {
        this.patient = patient;
        this.clinic = clinic;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.purpose = purpose;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}