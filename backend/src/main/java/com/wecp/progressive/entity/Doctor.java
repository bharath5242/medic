package com.wecp.progressive.entity;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Doctor implements Comparable<Doctor> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int doctorId;
    
    private String fullName;
    private String specialty;
    private String contactNumber;
    private String email;
    private int yearsOfExperience;

    // @ManyToOne
    // private Clinic clinic;

    public Doctor() {
    }
    
    public Doctor(int doctorId, String fullName, String specialty, String contactNumber, String email,
            int yearsOfExperience) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String speciality) {
        this.specialty = speciality;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public int compareTo(Doctor o) {
        // return this.getYearsOfExperience() - o.getYearsOfExperience();
        return Comparator.comparingInt(Doctor::getYearsOfExperience).compare(this, o);
    }

}