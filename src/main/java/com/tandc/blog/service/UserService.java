package com.tandc.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.tandc.blog.dto.UserDto;
import com.tandc.blog.entity.User;
import com.tandc.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
		
	public ResponseEntity<UserDto> getUserByID(Long id) {
		User user= userRepository.findById(id).get();
		UserDto userDto =  converttoDto(user);
		return ResponseEntity.ok(userDto);
	}
	
	public UserDto converttoDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		userDto.setId(user.getId());
		userDto.setRole(user.getRolesName());
		return userDto;
	}

	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<User> users = userRepository.findAll();
		
		return ResponseEntity.ok(users.stream().map(this::converttoDto).collect(Collectors.toList()));
	}
	 
}
	 

