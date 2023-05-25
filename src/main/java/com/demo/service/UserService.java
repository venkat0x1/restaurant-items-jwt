package com.demo.service;

import com.demo.dto.AuthRequest;
import com.demo.entity.User;
import com.demo.exception.ArgumentsMismatchException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.exception.UserUnauthorizedException;
import com.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user credentials");
        }
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }

    private String getUserMail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public ResponseEntity<User> addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(user.getRoles() == null ? "USER" : user.getRoles());
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            throw new ArgumentsMismatchException("Invalid user details");
        }
    }


    public Page<User> getAllUsers(int offset, int pageSize, String sortBy, String orderDirection) {
        return userRepository.findAll(RestaurantService.getPageable(offset,pageSize,sortBy,orderDirection));
    }

    public ResponseEntity<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        User existingUser = user.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (!isAdmin() && !getUserMail().equals(existingUser.getMail())) {
            throw new UserUnauthorizedException("You are not authorized to get this user's details.");
        }
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<User> getUserByMail(String mail) {
        Optional<User> user = userRepository.findByMail(mail);
        User existingUser = user.orElseThrow(() -> new ResourceNotFoundException("User not found with mail: " + mail));
        if (!isAdmin() && !getUserMail().equals(existingUser.getMail())) {
            throw new UserUnauthorizedException("You are not authorized to get this user's details.");
        }
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<String> deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        } else {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
    }

    public ResponseEntity<String> deleteUserByUsername(String mail) {
        Optional<User> userOptional = userRepository.findByMail(mail);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with mail: " + mail);
        } else {
            userRepository.deleteByMail(mail);
            return ResponseEntity.ok("User deleted successfully");
        }
    }

    public ResponseEntity<String> updateUserByMail(String mail, User updateUser) {
        Optional<User> existingUser = userRepository.findByMail(mail);
        User user = existingUser.orElseThrow(() -> new ResourceNotFoundException("User not found with mail: " + mail));
        if (!isAdmin() && updateUser.getRoles() != null) {
            throw new UserUnauthorizedException("You are not authorized for changing user's roles.");
        }
        user.setName(updateUser.getName() != null ? updateUser.getName() : user.getName());
        user.setMobile(updateUser.getMobile() != null ? updateUser.getMobile() : user.getMobile());
        user.setMail(updateUser.getMail() != null ? updateUser.getMail() : user.getMail());
        user.setPassword(updateUser.getPassword() != null ? updateUser.getPassword() : user.getPassword());
        user.setRoles(updateUser.getRoles() != null ? updateUser.getRoles() : user.getRoles());
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new ArgumentsMismatchException("Invalid input for user update");
        }
        return ResponseEntity.ok("User updated successfully");
    }
}
