package com.descentrilizedsynergy.supdem.consumer.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.descentrilizedsynergy.supdem.consumer.db.repository.ConsumerProfileRepository;
import com.descentrilizedsynergy.supdem.consumer.model.LoginRequest;

@Service
public class AuthenticationService {
    private final ConsumerProfileRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            ConsumerProfileRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // public ProducerProfile signup(RegisterUserTO input) throws
    // ProfileAlreadyExistException {
    // ProducerProfile user = new ProducerProfile();
    // user.setName(input.getName());
    // user.setLastName(input.getLastName());
    // user.setEmail(input.getEmail());
    // user.setAddress(input.getAddress());
    // user.setPassword(passwordEncoder.encode(input.getPassword()));

    // ProducerProfile newUser = null;
    // try {
    // newUser = userRepository.save(user);
    // } catch (Exception e) {
    // throw new ProfileAlreadyExistException();
    // }
    // return newUser;
    // }

    public UserDetails authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
