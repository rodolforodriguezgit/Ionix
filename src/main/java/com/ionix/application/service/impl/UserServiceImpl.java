package com.ionix.application.service.impl;

import com.ionix.application.mapper.UserMapper;
import com.ionix.application.service.UserService;
import com.ionix.domain.dto.UserRequest;
import com.ionix.domain.dto.UserResponse;
import com.ionix.domain.model.User;
import com.ionix.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creando usuario con email: {}", userRequest.getEmail());
        
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("El usuario con email " + userRequest.getEmail() + " ya existe");
        }
        
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("El usuario con nombre de usuario " + userRequest.getUsername() + " ya existe");
        }
        
        User user = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);
        log.info("Usuario creado exitosamente con id: {}", savedUser.getId());
        
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("Recuperando todos los usuarios");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.info("Recuperando usuario por email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con email " + email + " no encontrado"));
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Eliminando usuario con id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario con id " + id + " no encontrado");
        }
        userRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente con id: {}", id);
    }
}