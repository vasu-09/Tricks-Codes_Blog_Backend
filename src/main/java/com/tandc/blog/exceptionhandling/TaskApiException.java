package com.tandc.blog.exceptionhandling;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskApiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String message;
}
