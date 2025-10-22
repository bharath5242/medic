package com.wecp.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wecp.progressive.entity.Doctor;
import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor,Integer>
{
    // List<Doctor> findByDoctorId(int doctorId);
    Doctor findByDoctorId(Integer doctorId);
    Doctor findByEmail(String email);
}
