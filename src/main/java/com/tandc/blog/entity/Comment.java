
package com.tandc.blog.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
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
public class Comment {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @Lob
	  @Column(columnDefinition = "LONGTEXT")
	  private String content;
	    
	  @ManyToOne
	  @JoinColumn(name = "article_id")
	  private Article article;
	    
	  @ManyToOne
	  @JoinColumn(name = "user_id")
	  private User user;
	  
	  @CreationTimestamp
	   private LocalDateTime createdAt;
	  
	  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	  private List<Reply> replies;

	  
}
