package com.wecp.progressive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wecp.progressive.entity.Patient;
import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    // @Query("SELECT p FROM Patient p WHERE p.patientId = :patientId")
    Patient findByPatientId(Integer patientId);

    @Query("SELECT p FROM Patient p ORDER BY p.fullName ASC")
    List<Patient> getAllPatientSortedByName();

    Patient findByEmail(String email);
}
