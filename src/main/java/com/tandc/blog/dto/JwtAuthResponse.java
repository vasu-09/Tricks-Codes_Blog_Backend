package com.tandc.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private String role;
	private String user;
}
