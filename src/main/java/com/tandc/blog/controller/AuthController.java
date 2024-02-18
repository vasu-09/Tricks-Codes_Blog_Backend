package com.tandc.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tandc.blog.dto.JwtAuthResponse;
import com.tandc.blog.dto.LoginDto;
import com.tandc.blog.dto.RegisterDto;
import com.tandc.blog.service.AuthService;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping("auth")
public class AuthController {
	@Autowired
    private AuthService authService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.registerUser(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
    	JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
		
		return ResponseEntity.ok(jwtAuthResponse);
    }


}
