
package com.tandc.blog.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tandc.blog.dto.JwtAuthResponse;
import com.tandc.blog.dto.LoginDto;
import com.tandc.blog.dto.RegisterDto;
import com.tandc.blog.entity.Role;
import com.tandc.blog.entity.User;
import com.tandc.blog.exceptionhandling.TaskApiException;
import com.tandc.blog.repository.RoleRepository;
import com.tandc.blog.repository.UserRepository;
import com.tandc.blog.security.JwtTokenProvider;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public String registerUser(RegisterDto registerDto) {
				
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TaskApiException(HttpStatus.BAD_REQUEST, "Email already exist");
		}
		
		User user= new User();
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode("User@$1234"));
		
		
		Set<Role> roles = new HashSet<>();
		Role role =roleRepository.findByName("ROLE_USER");
		roles.add(role);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User Registored Successfully!. Navigate to Login Page and login with user name and password!";
	}
	
	public JwtAuthResponse login(LoginDto loginDto) {

		Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		
		boolean result = authentication.isAuthenticated();
		
		if(result) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		String token= jwtTokenProvider.generateToke(authentication);
		
		Optional<User> optionalUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Role> optionalRole = user.getRoles().stream().findFirst();
            String role = optionalRole.map(Role::getName).orElse(null);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setRole(role);
            jwtAuthResponse.setUser(user.getUsername());
            jwtAuthResponse.setAccessToken(token);

            return jwtAuthResponse;
        } else {
            return null;
        }   
        
	}
}
