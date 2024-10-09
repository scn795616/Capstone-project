package com.crimsonlogic.freelancersystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
    	 try {
             userService.createUser(userDTO);
             return ResponseEntity.ok("User registered successfully!");
         } catch (Exception e) {
             return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
         }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getalluser")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO,HttpServletRequest request) {
        try {
            UserDTO loggedInUser = userService.login(userDTO);
//            HttpServletRequest request=null;
            request.getSession().setAttribute("user", loggedInUser);
            
            System.out.println(request.getSession().getAttribute("user"));
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

