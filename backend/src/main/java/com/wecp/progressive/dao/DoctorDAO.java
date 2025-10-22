package com.wecp.progressive.dao;

import com.wecp.progressive.entity.Doctor;

import java.util.List;

public interface DoctorDAO {
    int addDoctor(Doctor doctor);
    Doctor getDoctorById(int doctorId);
    void updateDoctor(Doctor doctor);
    void deleteDoctor(int doctorId) ;
    List<Doctor> getAllDoctors();
}
