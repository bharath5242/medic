package com.wecp.progressive.service.impl;

import com.wecp.progressive.dto.UserRegistrationDTO;
import com.wecp.progressive.entity.Doctor;
import com.wecp.progressive.entity.Patient;
import com.wecp.progressive.entity.User;
import com.wecp.progressive.repository.DoctorRepository;
import com.wecp.progressive.repository.PatientRepository;
import com.wecp.progressive.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) throws Exception {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception {
        if (userRepository.findByUsername(userRegistrationDTO.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        if (!userRegistrationDTO.getRole().equalsIgnoreCase("PATIENT") && !userRegistrationDTO.getRole().equalsIgnoreCase("DOCTOR")) {
            throw new RuntimeException("Invalid role. Only 'PATIENT' or 'DOCTOR' allowed.");
        }
        // if (userRepository.findByUsername(userRegistrationDTO.getUsername()) != null)
        // {
        // if (!userRegistrationDTO.getRole().equalsIgnoreCase("PATIENT")
        // || !userRegistrationDTO.getRole().equalsIgnoreCase("DOCTOR")) {
        // throw new RuntimeException("Invalid role. Only 'PATIENT' or 'DOCTOR'
        // allowed.");
        // }
        // }

        User user = new User();
        if (userRegistrationDTO.getRole().equalsIgnoreCase("PATIENT")) {
            Patient patient = new Patient();
            patient.setFullName(userRegistrationDTO.getFullName());
            patient.setDateOfBirth(userRegistrationDTO.getDateOfBirth());
            patient.setAddress(userRegistrationDTO.getAddress());
            patient.setEmail(userRegistrationDTO.getEmail());
            patient.setContactNumber(userRegistrationDTO.getContactNumber());

            Patient savedPatient = patientRepository.save(patient);
            user.setPatient(savedPatient);
        } else if (userRegistrationDTO.getRole().equalsIgnoreCase("DOCTOR")) {
            Doctor doctor = new Doctor();
            doctor.setFullName(userRegistrationDTO.getFullName());
            doctor.setEmail(userRegistrationDTO.getEmail());
            doctor.setContactNumber(userRegistrationDTO.getContactNumber());
            doctor.setSpecialty(userRegistrationDTO.getSpecialty());
            doctor.setYearsOfExperience(userRegistrationDTO.getYearsOfExperience());

            Doctor savedDoctor = doctorRepository.save(doctor);
            user.setDoctor(savedDoctor);
        }

        // System.out.println(userRegistrationDTO.getUsername()+"username");
        user.setUsername(userRegistrationDTO.getUsername());
        // System.out.println("Here");
        // System.out.println(userRegistrationDTO.getPassword()+"password");
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        // System.out.println("Here-2");
        user.setRole(userRegistrationDTO.getRole());

        userRepository.save(user);
    }

    public User getUserDetails(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        // if (user == null) {
        // throw new UsernameNotFoundException("User not found");
        // }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

    }
}
