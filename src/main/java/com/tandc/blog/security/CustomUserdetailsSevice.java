package com.tandc.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tandc.blog.entity.User;
import com.tandc.blog.repository.UserRepository;


@Service
public class CustomUserdetailsSevice implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String usernameoremail) throws UsernameNotFoundException {
		User user= userRepository.findByUsernameOrEmail(usernameoremail, usernameoremail)
				.orElseThrow(() -> new UsernameNotFoundException("User not exists by username or email"));
		
		 Set<GrantedAuthority> authorities = user.getRoles().stream()
	                .map((role) -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(
				usernameoremail, 
				user.getPassword(), 
				authorities
				);
		}

}
