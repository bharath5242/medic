package com.wecp.progressive.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wecp.progressive.config.DatabaseConnectionManager;
import com.wecp.progressive.entity.Clinic;
import com.wecp.progressive.entity.Doctor;

public class ClinicDAOImpl implements ClinicDAO {

    @Override
    public int addClinic(Clinic clinic) throws SQLException {
        String query = "insert into clinic(clinic_name, location, doctor_id, contact_number, established_year) values(?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());
            ps.setInt(3, clinic.getDoctorId());
            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());

            int count = ps.executeUpdate();
            if (count > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    clinic.setClinicId(id);
                    return id;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    

    @Override
    public void deleteClinic(int clinicId) throws SQLException {
        String query = "delete from clinic where clinic_id = ?";

        try{
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query);
            ps.setInt(1, clinicId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Clinic> getAllClinics() throws SQLException {
        String query = "select * from clinic";
        List<Clinic> clinics = new ArrayList<>();
        try (Statement ps = DatabaseConnectionManager.getConnection().createStatement()) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                clinics.add(mapper(rs));
            }
        }
        return clinics;
    }

    @Override
    public Clinic getClinicById(int clinicId) throws SQLException {
        String query = "select * from clinic where clinic_id = ?";

        try(PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query)){
            ps.setInt(1, clinicId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return mapper(rs);
            }
        }
        return null;
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        String query = "update clinic set clinic_name = ?, location = ?, doctor_id = ?, contact_number = ?, established_year = ? where clinic_id = ?";
        try (PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query)) {
            ps.setString(1, clinic.getClinicName());
            ps.setString(2, clinic.getLocation());
            ps.setInt(3, clinic.getDoctorId());
            ps.setString(4, clinic.getContactNumber());
            ps.setInt(5, clinic.getEstablishedYear());
            ps.setInt(6, clinic.getClinicId());

            ps.executeUpdate();
        }
    }

    public Clinic mapper(ResultSet rs) throws SQLException{
        Clinic c = new Clinic();
        c.setClinicId(rs.getInt("clinic_id"));
        c.setClinicName(rs.getString("clinic_name"));
        c.setLocation(rs.getString("location"));
        c.setDoctorId(rs.getInt("doctor_id"));
        c.setContactNumber(rs.getString("contact_number"));
        c.setEstablishedYear(rs.getInt("established_year"));
        return c;
    }

}
