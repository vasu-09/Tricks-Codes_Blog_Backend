package com.tandc.blog.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    
    @CreationTimestamp
    @Column(name = "created_date")
	private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    
    @ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

    // other fields...

    // getters and setters...
}

