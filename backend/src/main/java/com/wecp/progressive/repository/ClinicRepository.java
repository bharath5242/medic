package com.wecp.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wecp.progressive.entity.Clinic;
import java.util.List;


@Repository
public interface ClinicRepository extends JpaRepository<Clinic,Integer> 
{
    Clinic findByClinicId(int clinicId);
    List<Clinic> findAllByLocation(String location);

    @Query("SELECT c FROM Clinic c WHERE c.doctorId = :doctorId")
    List<Clinic> findClinicByDoctorId(int doctorId);

    Clinic findByClinicName(String clinicName);
}
