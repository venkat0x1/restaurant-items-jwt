package com.demo.controller;

import com.demo.dto.AuthRequest;
import com.demo.dto.LoginResponse;
import com.demo.dto.UserDto;
import com.demo.entity.User;
import com.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication")
public class AuthenticationController {


   @Autowired
   private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticateAndGetToken(authRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> addNewUser(@RequestBody UserDto userDto) {
        return authenticationService.addUser(userDto);
    }




}
