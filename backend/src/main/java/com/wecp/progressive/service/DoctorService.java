package com.wecp.progressive.service;

import com.wecp.progressive.dto.DoctorDTO;
import com.wecp.progressive.entity.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DoctorService {

    public List<Doctor> getAllDoctors() throws SQLException;

    public Integer addDoctor(Doctor doctor) throws SQLException;

    public List<Doctor> getDoctorSortedByExperience() throws Exception;

    default void emptyArrayList() throws Exception {
    }

    //Do not implement these methods in DoctorServiceImplArraylist.java class
    default public void updateDoctor(Doctor doctor) throws Exception { }

    default public void deleteDoctor(int doctorId) throws Exception { }

    default Doctor getDoctorById(int doctorId) throws Exception { return null;}

    //Do not implement these methods in DoctorServiceImplArraylist.java and DoctorServiceImplJdbc.java class
    // Do not implement this method until day-13
    default public void modifyDoctorDetails(DoctorDTO doctorDTO) { }
}
