package com.tandc.blog.dto;

import java.util.List;

import com.tandc.blog.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatArtdto {

	private Long id;
	private String name;
	private List<String> artname;
	private List<Long> artid;
	
	 public CatArtdto(Category category) {
	        this.id = category.getId();
	        this.name = category.getName();
	        this.artname = category.getArticleTitles();
	        this.artid=category.getArticleIds();
	    }
	 
}
