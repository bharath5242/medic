package com.wecp.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.entity.Patient;
import com.wecp.progressive.service.PatientService;

@Service
public class PatientServiceImplArraylist implements PatientService {

    private static List<Patient> patientList = new ArrayList<>();

    @Override
    public void emptyArrayList() {
        patientList = new ArrayList<>();
    }

    @Override
    public Integer addPatient(Patient patient) {

        // patientList.add(patient);
        // return patientList.size();
        try{
            patientList.add(patient);
            return patientList.size();
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Patient> getAllPatientSortedByName()  {
        // Collections.sort(patientList);
        // return patientList;
        List<Patient> sortedLst = new ArrayList<>(patientList);
        sortedLst.sort(Comparator.comparing(Patient::getFullName));
        return sortedLst;
        // Comparator<Patient> comp = new Comparator<Patient>() {
        // @Override
        // public int compare(Patient p1, Patient p2) {
        // return p1.getFullName().compareTo(p2.getFullName());
        // }
        // };
        
        // sortedLst.sort(comp);
        
    }

    @Override
    public List<Patient> getAllPatients()  {
        // return patientList;
        return new ArrayList<>(patientList);
    }

    @Override
    public Patient getPatientById(int patientId){
        return patientList.stream().filter(p-> p.getPatientId() == patientId).findFirst().orElse(null);
    }

}