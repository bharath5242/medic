package com.wecp.progressive.service.impl;

import com.wecp.progressive.dto.UserRegistrationDTO;
import com.wecp.progressive.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserLoginServiceImpl extends UserDetailsService {

    User createUser(User user) throws Exception;

    User getUserByUsername(String username) throws Exception;

    void registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception;

    User getUserDetails(int userId);
}
