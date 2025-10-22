package com.wecp.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.wecp.progressive.dao.ClinicDAO;
import com.wecp.progressive.entity.Clinic;
import com.wecp.progressive.service.ClinicService;

public class ClinicServiceImplJdbc implements ClinicService {

    private ClinicDAO clinicDAO;

    public ClinicServiceImplJdbc(ClinicDAO clinicDAO) {
        this.clinicDAO = clinicDAO;
    }

    @Override
    public Integer addClinic(Clinic clinic) {
        try {
            Integer id = clinicDAO.addClinic(clinic);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteClinic(int clinicId) {
        try {
            clinicDAO.deleteClinic(clinicId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Clinic> getAllClinics() {
        try {
            List<Clinic> list = clinicDAO.getAllClinics();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Clinic getClinicById(int clinicId) {
        try {
            Clinic clinic = clinicDAO.getClinicById(clinicId);
            return clinic;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        try {
            clinicDAO.updateClinic(clinic);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}