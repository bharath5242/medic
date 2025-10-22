package com.wecp.progressive.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wecp.progressive.config.DatabaseConnectionManager;
import com.wecp.progressive.entity.Patient;

public class PatientDAOImpl implements PatientDAO {
    // private Connection connection;

    // public PatientDAOImpl(Connection connection){
    //     try {
    //         connection = DatabaseConnectionManager.getConnection();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
        
    // }

    @Override
    public int addPatient(Patient patient) throws SQLException {
        
        String query = "insert into patient(full_name, date_of_birth, contact_number, email, address) values(?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, patient.getFullName());
            ps.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime()));
            ps.setString(3, patient.getContactNumber());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getAddress());

            int count = ps.executeUpdate();
            if(count > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    patient.setPatientId(id);
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
    public void deletePatient(int patientId) throws SQLException {
        String query = "delete from patient where patient_id = ?";

        try(PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query)){
            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        String query = "select * from patient";
        List<Patient> li = new ArrayList<>();
        try(Statement s = DatabaseConnectionManager.getConnection().createStatement()){
            ResultSet rs = s.executeQuery(query);
            
            while(rs.next()){
                li.add(mapper(rs));
            }
        }
        return li;
    }

    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        String query = "select * from patient where patient_id = ?";

        try(PreparedStatement ps = DatabaseConnectionManager.getConnection()
        .prepareStatement(query)){
            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return mapper(rs);
            }
        }
        return null;
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        String query = "update patient set full_name = ?, date_of_birth = ?, contact_number = ?, email = ?, address = ? where patient_id = ?";

        try{
            PreparedStatement ps = DatabaseConnectionManager.getConnection().prepareStatement(query);
            ps.setString(1, patient.getFullName());
            ps.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime()));   //new java.sql.Date(utilDate.getTime())
            ps.setString(3, patient.getContactNumber());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getAddress());
            ps.setInt(6, patient.getPatientId());

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatientsSortedByName() throws SQLException {
        String query = "select * from patient order by full_name";
        List<Patient> li = new ArrayList<>();
        try(Statement s = DatabaseConnectionManager.getConnection().createStatement()){
            ResultSet rs = s.executeQuery(query);
            
            while(rs.next()){
                li.add(mapper(rs));
            }
        }
        return li;
    }

    public Patient mapper(ResultSet rs) throws SQLException{
        Patient p = new Patient();
        p.setPatientId(rs.getInt("patient_id"));
        p.setFullName(rs.getString("full_name"));
        p.setEmail(rs.getString("email"));
        p.setDateOfBirth(rs.getDate("date_of_birth"));
        p.setContactNumber(rs.getString("contact_number"));
        p.setAddress(rs.getString("address"));

        return p;
    }

}
