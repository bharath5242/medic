package com.wecp.progressive.controller;

import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
            return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int doctorId) {
        try {
            return new ResponseEntity<>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addDoctor(@RequestBody Doctor doctor) {
        try {
            return new ResponseEntity<>(doctorService.addDoctor(doctor), HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<Void> updateDoctor(@PathVariable int doctorId, @RequestBody Doctor doctor) {
        try {
            Doctor doc = doctorService.getDoctorById(doctorId);
            if(doc == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            doctorService.updateDoctor(doctor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int doctorId) {
        try {
            Doctor doc = doctorService.getDoctorById(doctorId);
            if(doc == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            doctorService.deleteDoctor(doctorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/experience")
    public ResponseEntity<List<Doctor>> getDoctorSortedByExperience() {
        try {
            return new ResponseEntity<>(doctorService.getDoctorSortedByExperience(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
