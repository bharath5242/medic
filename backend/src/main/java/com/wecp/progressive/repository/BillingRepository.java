package com.wecp.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wecp.progressive.entity.Billing;
import com.wecp.progressive.entity.Patient;

import java.util.List;


@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
    // List<Billing> findByPatient(int patientId);
    List<Billing> findByPatient_PatientId(int patientId);
    
    @Query("DELETE FROM Billing b WHERE b.patient.patientId = :patientId")
    void deleteByPatientId(int patientId);
}
