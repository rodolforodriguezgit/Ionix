package com.ionix.application.service;

import com.ionix.domain.dto.UserRequest;
import com.ionix.domain.dto.UserResponse;

import java.util.List;

public interface UserService {
    
    UserResponse createUser(UserRequest userRequest);
    
    List<UserResponse> getAllUsers();
    
    UserResponse getUserByEmail(String email);
    
    void deleteUser(Long id);
}
