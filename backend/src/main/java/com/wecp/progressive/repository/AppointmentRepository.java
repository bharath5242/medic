package com.wecp.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wecp.progressive.entity.Appointment;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByClinicClinicId(int clinicId);
    List<Appointment> findByPatientPatientId(int patientId);
    List<Appointment> findByStatus(String status);

    // @Query("DELETE FROM Appointment a WHERE a.patientId = :patientId")
    // void deleteByPatientId(int patientId);

    // @Query("DELETE FROM Appointment a WHERE a.doctorId = :doctorId")
    // void deleteByDoctorId(int doctorId);

    // @Query("DELETE FROM Appointment a WHERE a.clinicId = :clinicId")
    // void deleteByClinicId(int clinicId);
}
