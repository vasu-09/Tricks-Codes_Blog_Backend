package com.tandc.blog.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Commentget {
	private Long id;
	
	private String content;
	
	private LocalDateTime createdAt;
	
	private String userEmail;
	
	private String userName;
	 

}
