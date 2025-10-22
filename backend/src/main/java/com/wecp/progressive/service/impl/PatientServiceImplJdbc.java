package com.wecp.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wecp.progressive.dao.PatientDAO;
import com.wecp.progressive.entity.Patient;
import com.wecp.progressive.service.PatientService;

public class PatientServiceImplJdbc implements PatientService {

    private PatientDAO patientDAO;

    public PatientServiceImplJdbc(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    public Integer addPatient(Patient patient) throws Exception {
        Integer id = patientDAO.addPatient(patient);
        return id;
        // try {
        // Integer id = patientDAO.addPatient(patient);
        // return id;
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return 0;
    }

    @Override
    public void deletePatient(int patientId) throws Exception {
        // TODO Auto-generated method stub
        // PatientService.super.deletePatient(patientId);
        // patientDAO.deletePatient(patientId);
        try {
            patientDAO.deletePatient(patientId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void emptyArrayList() {
        // TODO Auto-generated method stub
        // PatientService.super.emptyArrayList();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        List<Patient> patients = patientDAO.getAllPatients(); 
        Collections.sort(patients, new Comparator<Patient>(){
            @Override
            public int compare(Patient p1, Patient p2){
                return p1.getFullName().compareTo(p2.getFullName());
            }
        });
        return patients;
    }

    @Override
    public List<Patient> getAllPatients() throws Exception {
        // TODO Auto-generated method stub
        List<Patient> list = patientDAO.getAllPatients();
        return list;

        // try {
        // List<Patient> list = patientDAO.getAllPatients();
        // return list;
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return List.of();
    }

    @Override
    public Patient getPatientById(int patientId) throws Exception {
        // TODO Auto-generated method stub
        // return PatientService.super.getPatientById(patientId);
        Patient p = patientDAO.getPatientById(patientId);
        return p;
        // try {
        // Patient p = patientDAO.getPatientById(patientId);
        // return p;
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return null;
    }

    @Override
    public void updatePatient(Patient patient) throws Exception {
        // TODO Auto-generated method stub
        // PatientService.super.updatePatient(patient);
        patientDAO.updatePatient(patient);

        // try {
        // patientDAO.updatePatient(patient);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

}