package com.wecp.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wecp.progressive.entity.Clinic;
import com.wecp.progressive.exception.ClinicAlreadyExistsException;
import com.wecp.progressive.repository.ClinicRepository;
import com.wecp.progressive.service.ClinicService;

@Service
public class ClinicServiceImplJpa implements ClinicService {
    // @Autowired
    private ClinicRepository clinicRepository;

    public ClinicServiceImplJpa(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public Integer addClinic(Clinic clinic) throws Exception {
        Clinic cl = clinicRepository.findByClinicName(clinic.getClinicName());
        if (cl != null) {
            throw new ClinicAlreadyExistsException("Clinic already exists");
        }
        return clinicRepository.save(clinic).getClinicId();
    }

    @Override
    public void deleteClinic(int clinicId) throws Exception {
        if (!clinicRepository.existsById(clinicId)) {
            throw new Exception();
        }
        clinicRepository.deleteById(clinicId);
    }

    @Override
    public List<Clinic> getAllClinicByDoctorId(int doctorId) throws Exception {
        return clinicRepository.findClinicByDoctorId(doctorId);
    }

    @Override
    public List<Clinic> getAllClinicByLocation(String location) throws Exception {
        System.out.println(location);
        return clinicRepository.findAllByLocation(location);
    }

    @Override
    public List<Clinic> getAllClinics() throws Exception {
        return clinicRepository.findAll();
    }

    @Override
    public Clinic getClinicById(int clinicId) throws Exception {
        return clinicRepository.findByClinicId(clinicId);
    }

    @Override
    public void updateClinic(Clinic clinic) throws Exception {
        Clinic c = clinicRepository.findByClinicId(clinic.getClinicId());
        if (c == null) {
            throw new Exception();
        }

        c.setClinicName(clinic.getClinicName());
        c.setContactNumber(clinic.getContactNumber());
        c.setLocation(clinic.getLocation());
        c.setDoctorId(clinic.getDoctorId());
        // c.setDoctorId(clinic.getDoctor().getDoctorId());
        c.setEstablishedYear(clinic.getEstablishedYear());
        // c.setDoctor(clinic.getDoctor());

        clinicRepository.save(c);
    }

}