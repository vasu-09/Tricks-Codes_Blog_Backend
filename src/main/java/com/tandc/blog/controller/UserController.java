package com.tandc.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.tandc.blog.dto.UserDto;
import com.tandc.blog.service.UserService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("all")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("get/{id}")
	public ResponseEntity<UserDto> getUserbyId(@PathVariable Long id){
		return userService.getUserByID(id);
	}
	

	

}
