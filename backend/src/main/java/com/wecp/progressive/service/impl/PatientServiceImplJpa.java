package com.wecp.progressive.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wecp.progressive.dto.PatientDTO;
import com.wecp.progressive.entity.Patient;
import com.wecp.progressive.exception.PatientAlreadyExistsException;
import com.wecp.progressive.exception.PatientNotFoundException;
import com.wecp.progressive.repository.BillingRepository;
import com.wecp.progressive.repository.PatientRepository;
import com.wecp.progressive.service.PatientService;

@Service
public class PatientServiceImplJpa implements PatientService {
    // @Autowired
    private PatientRepository patientRepository;

    public PatientServiceImplJpa(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    private BillingRepository billingRepository;

    public Integer addPatient(Patient patient) throws Exception {
        // Check if patient with same email already exists
        Patient existingPatient = patientRepository.findByEmail(patient.getEmail());
        if (existingPatient != null) {
            throw new PatientAlreadyExistsException("Patient with email " + patient.getEmail() + " already exists");
        }
        Patient p = patientRepository.save(patient);
        return p.getPatientId();
    }

    @Override
    public void deletePatient(int patientId) throws PatientNotFoundException {
        // Optional<Patient> p = patientRepository.findById(patientId);
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException("Patient not found");
        }

        billingRepository.deleteByPatientId(patientId);

        patientRepository.deleteById(patientId);
    }

    @Override
    public void emptyArrayList() throws Exception {
        PatientService.super.emptyArrayList();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        // return patientRepository.getAllPatientSortedByName();
        List<Patient> list = patientRepository.findAll();
        list.sort(Comparator.comparing(Patient::getFullName));
        return (list);
    }

    @Override
    public List<Patient> getAllPatients() throws Exception {
        List<Patient> li = patientRepository.findAll();
        return li;
    }

    @Override
    public Patient getPatientById(int patientId) throws Exception {
        Patient p = patientRepository.findByPatientId(patientId);
        if (p == null) {
            throw new PatientNotFoundException("Patient not found");
        }

        return p;
    }

    @Override
    public void updatePatient(Patient patient) throws Exception {

        // Patient p = patientRepository.findByEmail(patient.getEmail());
        Patient p = patientRepository.findById(patient.getPatientId()).get();
        // System.out.println("patit in service: " + p);
        // if(p == null){
        // throw new PatientNotFoundException("Patient not found");
        // }
        p.setFullName(patient.getFullName());
        p.setEmail(patient.getEmail());
        // System.out.println(patient.getDateOfBirth());
        p.setDateOfBirth(patient.getDateOfBirth());
        p.setContactNumber(patient.getContactNumber());
        p.setAddress(patient.getAddress());

        patientRepository.save(p);

        // TODO Auto-generated method stub
        // PatientService.super.updatePatient(patient);
    }

    // @Override
    // public void modifyPatientDetails(PatientDTO patientDTO) {
    // // TODO Auto-generated method stub
    // PatientService.super.modifyPatientDetails(patientDTO);
    // }

}