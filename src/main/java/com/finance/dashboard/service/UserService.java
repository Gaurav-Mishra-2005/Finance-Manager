package com.finance.dashboard.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.UserRegistrationDto;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new com.finance.dashboard.exception.ConflictException("User already exists with email: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); 
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());

        return userRepository.save(user);
    }
}