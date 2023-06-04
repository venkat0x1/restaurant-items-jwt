package com.demo.controller;

import com.demo.dto.AuthRequest;
import com.demo.dto.LoginResponse;
import com.demo.dto.UserDto;
import com.demo.entity.User;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/verify")
    public ResponseEntity<String> userVerification(@RequestParam String id) {
        return userService.userVerification(id);
    }

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return userService.getAllUsers(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable int id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        return userService.deleteUserById(id);
    }


}
