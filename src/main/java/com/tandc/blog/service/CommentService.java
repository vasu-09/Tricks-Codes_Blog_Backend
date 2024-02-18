
package com.tandc.blog.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tandc.blog.dto.CommentDto;
import com.tandc.blog.dto.Commentget;
import com.tandc.blog.dto.RegisterDto;
import com.tandc.blog.entity.Article;
import com.tandc.blog.entity.Comment;
import com.tandc.blog.entity.User;
import com.tandc.blog.repository.ArticleRepository;
import com.tandc.blog.repository.CommentRepository;
import com.tandc.blog.repository.ReplyRepository;
import com.tandc.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ReplyRepository replyRepository;
    
    @Autowired
    private AuthService authService;

    public ResponseEntity<List<Commentget>> getCommentsByArticleId(Long articleId) {
    	List<Comment> comments = commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
        return new ResponseEntity<>(comments.stream().map(this::convertToDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<Commentget> addComment(CommentDto commentDto, Long articleId) {
    	boolean exist = userRepository.existsByEmail(commentDto.getEmail());
    	Article article = articleRepository.findById(articleId).orElseThrow(null);
    	Comment comment = new Comment();
    	System.out.println("username:"+commentDto.getUsername()+"\temail:"+commentDto.getEmail());
    	if(exist) {
    		User user = userRepository.findByEmail(commentDto.getEmail()).get();
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        comment.setArticle(article);
        
        commentRepository.save(comment);
        
        Commentget commentget = convertToDTO(comment);
        return new ResponseEntity<>(commentget, HttpStatus.CREATED);
    	}
    	else {
    		RegisterDto registerDto = new RegisterDto();
    		registerDto.setEmail(commentDto.getEmail());
    		registerDto.setUsername(commentDto.getUsername());
    		
			authService.registerUser(registerDto);
			User user = userRepository.findByEmail(commentDto.getEmail()).get();
			
			
	        comment.setContent(commentDto.getContent());
	        comment.setUser(user);
	        comment.setArticle(article);
	        
	        commentRepository.save(comment);
	        
	        Commentget commentget = convertToDTO(comment);
	        return new ResponseEntity<>(commentget, HttpStatus.CREATED);
		}
    }
    
    public Commentget convertToDTO(Comment comment) {
    	Commentget commentget = new Commentget();
    	commentget.setId(comment.getId());
    	commentget.setContent(comment.getContent());
    	commentget.setCreatedAt(comment.getCreatedAt());
    	commentget.setUserEmail(comment.getUser().getEmail());
    	commentget.setUserName(comment.getUser().getUsername());
    	
    	return commentget;
    }
    // other methods...

	public ResponseEntity<Commentget> updateComment(CommentDto commentDto, Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

        	// Create a new comment
        	Comment comment = commentRepository.findById(id).get();
        	comment.setContent(commentDto.getContent());       
        	commentRepository.save(comment);
       
        	Commentget commentget = convertToDTO(comment);

       return new ResponseEntity<>(commentget, HttpStatus.CREATED);
        }else {
            // Handle the case where the user is not authenticated
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

	}

	public ResponseEntity<String> deleteComment(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
        	if(replyRepository.existsByCommentId(id)) {
        	replyRepository.deleteByCommentId(id);
        	commentRepository.deleteById(id);
        	}
        	else {
        	commentRepository.deleteById(id);
        	}
       return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.CREATED);
        }else {
            // Handle the case where the user is not authenticated
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
	}
}
