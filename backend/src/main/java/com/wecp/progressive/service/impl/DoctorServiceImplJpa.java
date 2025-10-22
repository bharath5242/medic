package com.wecp.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.exception.DoctorAlreadyExistsException;
import com.wecp.progressive.repository.DoctorRepository;
import com.wecp.progressive.service.DoctorService;

@Service
public class DoctorServiceImplJpa implements DoctorService  {

    // @Autowired
    private DoctorRepository doctorRepository;
    public DoctorServiceImplJpa(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Integer addDoctor(Doctor doctor) throws SQLException {
        Doctor tmp = doctorRepository.findByEmail(doctor.getEmail());
        if(tmp != null){
            throw new DoctorAlreadyExistsException("Doctor already exists");
        }
        Doctor doc = doctorRepository.save(doctor);
        return doc.getDoctorId();
    }

    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        if(!doctorRepository.existsById((Integer)doctorId)){
            throw new Exception();
        }
        doctorRepository.deleteById((Integer)doctorId);
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId);
        if(doctor == null){
            throw new Exception("Doctor with ID: "+doctorId+" not found");
        }
        return doctor;
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        List<Doctor> doctors = doctorRepository.findAll();
        Collections.sort(doctors, new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2){
                return d1.getYearsOfExperience() - d2.getYearsOfExperience();
            }
        });
        return doctors;
    }

    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        // Doctor doc = doctorRepository.findByEmail(doctor.getEmail());
        Doctor doc = doctorRepository.findByDoctorId(doctor.getDoctorId());
        // if(doc == null){
        //     throw new Exception();
        // }
        doc.setFullName(doctor.getFullName());
        doc.setContactNumber(doctor.getContactNumber());
        doc.setEmail(doctor.getEmail());
        doc.setSpecialty(doctor.getSpecialty());
        doc.setYearsOfExperience(doctor.getYearsOfExperience());

        doctorRepository.save(doc);
    }
}