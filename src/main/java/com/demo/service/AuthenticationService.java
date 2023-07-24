package com.demo.service;

import com.demo.dto.AuthRequest;
import com.demo.dto.LoginResponse;
import com.demo.dto.UserDto;
import com.demo.entity.User;
import com.demo.exception.InvalidInputException;
import com.demo.exception.UserUnauthorizedException;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailSenderService emailSenderService;

    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                LoginResponse loginResponse = new LoginResponse();
                Optional<User> optionalUser = userRepository.findByEmail(authRequest.getUsername());
                User user = optionalUser.get();
                if (user.getVerificationStatus().equals("pending")) {
                    emailSenderService.emailSending(user.getEmail(), user.getId());
                    throw new UserUnauthorizedException("check your email and conform your account verification");
                }
                loginResponse.setAccessToken(jwtService.generateToken(authRequest.getUsername()));
                loginResponse.setUser(user);
                return ResponseEntity.ok(loginResponse);
            } else {
                throw new InvalidInputException("Invalid user credentials");
            }
        } catch (AuthenticationException ex) {
            throw new InvalidInputException("Invalid user credentials");
        }
    }


    static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }

    static String getUserMail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
//        return authentication.getName();
    }


    public ResponseEntity<User> addUser(UserDto userDto) {
        try {
            User user = new User();
            user.setName(userDto.getName());
            user.setMobile(userDto.getMobile());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(userDto.getRoles().toUpperCase());
            user.setVerificationStatus("pending");
            User savedUser = userRepository.save(user);
            emailSenderService.emailSending(savedUser.getEmail(), savedUser.getId());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid Input..!");
        }
    }
}
