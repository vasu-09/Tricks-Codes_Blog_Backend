package com.tandc.blog.dto;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Articledto {
	private Long id; 
    private String title;
    private String content;
    private List<String> images;
    private LocalDateTime createdDate;
    private Long categoryid;
    

}
