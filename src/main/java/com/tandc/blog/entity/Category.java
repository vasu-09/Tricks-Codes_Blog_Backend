package com.tandc.blog.entity;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "category")
    private List<Article> articles;
    
    public List<String> getArticleTitles() {
        return articles.stream().map(Article::getTitle).collect(Collectors.toList());
    }
    
    public List<Long> getArticleIds() {
        return articles.stream().map(Article::getId).collect(Collectors.toList());
    }

}
