package com.wecp.progressive.controller;

import com.wecp.progressive.entity.Clinic;
import com.wecp.progressive.service.ClinicService;

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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        try {
            return new ResponseEntity<>(clinicService.getAllClinics(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable int clinicId) {
        try {
            Clinic clinic = clinicService.getClinicById(clinicId);
            if(clinic == null){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            // return new ResponseEntity<>(clinicService.getClinicById(clinicId), HttpStatus.OK);
            return new ResponseEntity<>(clinic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addClinic(@RequestBody Clinic clinic) {
        try {
            return new ResponseEntity<>(clinicService.addClinic(clinic), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{clinicId}")
    public ResponseEntity<Void> updateClinic(@PathVariable int clinicId, @RequestBody Clinic clinic) {
        try {
            Clinic cl = clinicService.getClinicById(clinicId);
            if(cl == null){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            clinicService.updateClinic(clinic);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{clinicId}")
    public ResponseEntity<Void> deleteClinic(@PathVariable int clinicId) {
        try {
            // clinicService.getAllClinics();
            clinicService.deleteClinic(clinicId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Clinic>> getAllClinicByLocation(@PathVariable String location) {
        try {
            return new ResponseEntity<>(clinicService.getAllClinicByLocation(location), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Clinic>> getAllClinicByDoctorId(@PathVariable int doctorId) {
        try {
            return new ResponseEntity<>(clinicService.getAllClinicByDoctorId(doctorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
