package com.portfolio.controllers;

import com.portfolio.dtos.LoginDto;
import com.portfolio.dtos.JwtResponseDto;
import com.portfolio.entities.User;
import com.portfolio.repositories.UserRepository;
import com.portfolio.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Authenticated user details not found in database."));

            JwtResponseDto responseDto = new JwtResponseDto(
                    jwtToken,
                    user.getEmail(),
                    user.getName(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
