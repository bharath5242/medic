package com.wecp.progressive.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wecp.progressive.config.DatabaseConnectionManager;
import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.entity.Patient;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public int addDoctor(Doctor doctor) {
        String query = "insert into doctor(full_name, specialty, contact_number, email, years_of_experience) values(?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());
            int count = ps.executeUpdate();
            if (count > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    doctor.setDoctorId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deleteDoctor(int doctorId) throws SQLException {
        String query = "delete from doctor where doctor_id = ?";

        try {
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query);
            ps.setInt(1, doctorId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        String query = "select * from doctor";
        List<Doctor> doctors = new ArrayList<>();
        try {
            Statement ps = DatabaseConnectionManager.getConnection().createStatement();
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                doctors.add(mapper(rs));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws SQLException {
        String query = "select * from doctor where doctor_id = ?";

        try {
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query);
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapper(rs);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDoctor(Doctor doctor) throws SQLException {
        String query = "update doctor set full_name = ?, specialty = ?, contact_number = ?, email = ?, years_of_experience = ? where doctor_id = ?";

        try {
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query);
            ps.setString(1, doctor.getFullName());
            ps.setString(2, doctor.getSpecialty());
            ps.setString(3, doctor.getContactNumber());
            ps.setString(4, doctor.getEmail());
            ps.setInt(5, doctor.getYearsOfExperience());
            ps.setInt(6, doctor.getDoctorId());

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    

    @Override
    public List<Doctor> getAllDoctorsSortedByName() throws SQLException {
        String query = "select * from doctor order by full_name";
        List<Doctor> doctors = new ArrayList<>();
        try {
            Statement ps = DatabaseConnectionManager.getConnection().createStatement();
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                doctors.add(mapper(rs));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return doctors;
    }

    public Doctor mapper(ResultSet rs) throws SQLException {
        Doctor p = new Doctor();
        p.setDoctorId(rs.getInt("doctor_id"));
        p.setFullName(rs.getString("full_name"));
        p.setEmail(rs.getString("email"));
        p.setSpecialty(rs.getString("specialty"));
        p.setContactNumber(rs.getString("contact_number"));
        p.setYearsOfExperience(rs.getInt("years_of_experience"));

        return p;
    }

}
