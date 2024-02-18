package com.tandc.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tandc.blog.security.JwtAuthenticationEntryPoint;
import com.tandc.blog.security.JwtAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntrypoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(request -> {request.requestMatchers("auth/register","auth/login","category/all","article/get/{id}","article/get","category/art/all", "comments/all/{id}", "comments/add/{id}","replies/get/{commentId}","user/all","about","/upload/image", "/uploads/**").permitAll();
			request.requestMatchers(HttpMethod.POST, "/category/add","article/post/{cat_id}","replies/add/{commentId}").hasRole("ADMIN");
			request.requestMatchers(HttpMethod.POST, "comments/add/{id}").hasAnyRole("ADMIN","USER");
			request.requestMatchers(HttpMethod.PUT, "article/put/{id}","update/about").hasRole("ADMIN");
			request.requestMatchers(HttpMethod.DELETE, "comments/delete/{id}", "article/delete/{id}").hasRole("ADMIN");
			request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
			request.anyRequest().authenticated();})
			.httpBasic(Customizer.withDefaults());
	
		// Jwt methods
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntrypoint));
	
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	
	   }

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/auth/register", "/auth/login", "/category/all", "/article/get/**", "/article/get", "/category/art/all", "/comments/all/**", "/comments/add/**", "/replies/get/**", "/user/all", "/about").permitAll()
//	            .requestMatchers(HttpMethod.POST, "/category/add", "/article/post/**", "/upload/image").hasRole("ADMIN")
//	            .requestMatchers(HttpMethod.POST, "/comments/add/**", "/replies/add/**").hasAnyRole("ADMIN", "USER")
//	            .requestMatchers(HttpMethod.PUT, "/article/put/**", "/update/about").hasRole("ADMIN")
//	            .requestMatchers(HttpMethod.DELETE, "/comments/delete/**").hasRole("ADMIN")
//	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//	            .anyRequest().authenticated()
//	        )
//	        .httpBasic(Customizer.withDefaults());
//
//	    // Jwt methods
//	    http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntrypoint));
//	    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//	    return http.build();
//	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
