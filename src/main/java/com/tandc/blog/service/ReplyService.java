package com.tandc.blog.service;

import java.time.LocalDateTime;
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
import com.tandc.blog.entity.Comment;
import com.tandc.blog.entity.Reply;
import com.tandc.blog.entity.User;
import com.tandc.blog.repository.CommentRepository;
import com.tandc.blog.repository.ReplyRepository;
import com.tandc.blog.repository.UserRepository;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<Commentget> addReply(CommentDto replyDto, Long commentId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String  username =  authentication.getName();
            User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
            Comment comment = commentRepository.findById(commentId).orElse(null);
            
            Reply reply = new Reply();
            reply.setComment(comment);
            reply.setContent(replyDto.getContent());
            reply.setCreatedAt(LocalDateTime.now());
            reply.setUser(user);
            
            replyRepository.save(reply);
            
            Commentget commentget = convertToDto(reply);
            
            return new ResponseEntity<>(commentget, HttpStatus.CREATED);
        }
		return null;
	}

	public ResponseEntity<List<Commentget>> getReplies(Long commentId) {
		List<Reply> replies = replyRepository.findByCommentIdOrderByCreatedAtDesc(commentId);
		return new ResponseEntity<>(replies.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	public Commentget convertToDto(Reply reply) {
		Commentget commentget = new Commentget();
		
		commentget.setId(reply.getId());
		
		commentget.setContent(reply.getContent());
		commentget.setCreatedAt(reply.getCreatedAt());
		commentget.setUserEmail(reply.getUser().getEmail());
		
		
		return commentget;
	}

	public ResponseEntity<String> deleteReply(Long id) {
		replyRepository.deleteById(id);
		
		return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
	}
}
