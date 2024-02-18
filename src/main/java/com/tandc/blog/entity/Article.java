
package com.tandc.blog.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Article {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    
    @CreationTimestamp
	private LocalDateTime createdDate;
    
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    public Long getCategoryId() {
        if (category != null) {
            return category.getId();
        }
        return null; // or throw an exception if category is expected to be non-null
    }
    
  
}

