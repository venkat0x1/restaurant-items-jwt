package com.demo.controller;

import com.demo.dto.AuthRequest;
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

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return "accessToken : "+userService.authenticateAndGetToken(authRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/all")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int offset,@RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String sortDirection) {
       return userService.getAllUsers(offset,pageSize,sortBy,sortDirection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<User> getUserByMail(@RequestParam String mail){
        return userService.getUserByMail(mail);
    }

    @PutMapping("/{mail}")
    public ResponseEntity<String> updateUserByMail(@PathVariable String mail, @RequestBody User user){
        return userService.updateUserByMail(mail,user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id){
        return userService.deleteUserById(id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserByMail(@RequestParam String mail){
      return  userService.deleteUserByUsername(mail);
    }

//    @PutMapping("/{id}/{roles}")
//    public ResponseEntity<String> updateUserRolesById(@PathVariable int id,@PathVariable String roles){
//        return userService.updateRolesById(id,roles);
//    }

}
