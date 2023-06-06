package com.demo.service;

import com.demo.entity.User;
import com.demo.exception.InvalidInputException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.exception.UserUnauthorizedException;
import com.demo.exception.VerificationException;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 */5 * * * *")
    public void triggerMail() {
        List<User> allUnverifiedUsers = userRepository.getUnverifiedUsers();
        for (User user : allUnverifiedUsers) {
            emailSenderService.emailSending(user.getEmail(),user.getId());
        }

    }

    public ResponseEntity<String> userVerification(String id) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid user ID format: " + id);
        }
        Optional<User> user = userRepository.findById(userId);
        User existingUser = user.orElseThrow(() -> new ResourceNotFoundException("User not found with Id : " + id));
        if(existingUser.getVerificationStatus().equals("completed")){
            throw new VerificationException("Verification was already completeed with id :"+id);
        }
        existingUser.setVerificationStatus("completed");
        userRepository.save(existingUser);
        return ResponseEntity.ok("User verification successful..!");
    }

    public Page<User> getAllUsers(int pageNumber, int pageSize, String sortBy, String orderDirection) {
        return userRepository.findAll(RestaurantService.getPageable(pageNumber, pageSize, sortBy, orderDirection));
    }

    public ResponseEntity<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        User existingUser = user.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (!AuthenticationService.isAdmin() && !AuthenticationService.getUserMail().equals(existingUser.getEmail())) {
            throw new UserUnauthorizedException("You are not authorized to get this user's details.");
        }
        return ResponseEntity.ok(existingUser);
    }


    public ResponseEntity<String> deleteUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id : " + id);
        } else {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
    }

    public ResponseEntity<User> updateUserById(int id, User updateUser) {
        Optional<User> existingUser = userRepository.findById(id);
        User user = existingUser.orElseThrow(() -> new ResourceNotFoundException("User not found with Id : " + id));
        if (!AuthenticationService.isAdmin() && updateUser.getRoles() != null) {
            throw new UserUnauthorizedException("You are not authorized for changing user's roles.");
        }
        user.setName(updateUser.getName() != null ? updateUser.getName() : user.getName());
        user.setMobile(updateUser.getMobile() != null ? updateUser.getMobile() : user.getMobile());
        user.setEmail(updateUser.getEmail() != null ? updateUser.getEmail() : user.getEmail());
        user.setPassword(updateUser.getPassword() != null ? updateUser.getPassword() : user.getPassword());
        user.setRoles(updateUser.getRoles() != null ? updateUser.getRoles() : user.getRoles());
        user.setVerificationStatus(updateUser.getVerificationStatus() !=null ? updateUser.getVerificationStatus():user.getVerificationStatus());
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new InvalidInputException("Invalid input for user update");
        }
        return ResponseEntity.ok(user);
    }
}
