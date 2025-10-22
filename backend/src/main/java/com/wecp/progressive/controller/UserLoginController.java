package com.wecp.progressive.controller;

import com.wecp.progressive.dto.LoginRequest;
import com.wecp.progressive.dto.UserRegistrationDTO;
import com.wecp.progressive.entity.User;
import org.springframework.http.ResponseEntity;

public class UserLoginController {
    public ResponseEntity<User> registerUser(UserRegistrationDTO registrationDTO) {
        return null;
    }

    public ResponseEntity loginUser(LoginRequest loginRequest) {
        return null;
    }

    public ResponseEntity<?> getUserDetails(int userId) {
        return null;
    }
}
