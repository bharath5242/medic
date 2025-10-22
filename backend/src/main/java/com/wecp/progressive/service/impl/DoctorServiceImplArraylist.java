package com.wecp.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.service.DoctorService;

public class DoctorServiceImplArraylist implements DoctorService  {

    private static List<Doctor> doctorList = new ArrayList<>();

    @Override
    public Integer addDoctor(Doctor doctor) {
        doctorList.add(doctor);
        return doctorList.size();
    }

    @Override
    public List<Doctor> getAllDoctors() {
       return doctorList;
    }

    @Override
    public void emptyArrayList(){
        doctorList = new ArrayList<>();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() {
        // Collections.sort(doctorList);
        // return doctorList;
        List<Doctor> sortedDoctors = doctorList;

        // Collections.sort(sortedDoctors, new Comparator<Doctor>() {
        //     @Override
        //     public int compare(Doctor d1, Doctor d2){
        //         return d1.getYearsOfExperience() - d2.getYearsOfExperience();
        //     }
        // });
        sortedDoctors.sort(Comparator.comparing(Doctor::getYearsOfExperience));
        return sortedDoctors;
    }

}