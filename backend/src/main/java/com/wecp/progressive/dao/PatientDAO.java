package com.wecp.progressive.dao;

import com.wecp.progressive.entity.Patient;

import java.util.List;

public interface PatientDAO {
    int addPatient(Patient patient);
    Patient getPatientById(int patientId);
    void updatePatient (Patient patient);
    void deletePatient (int patientId);
    List<Patient> getAllPatients();
}