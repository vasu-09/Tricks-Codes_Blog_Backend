package com.tandc.blog.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleRequest {
	private String title;
    private String content;
    private List<MultipartFile> images;
    private Long categoryId;
}
