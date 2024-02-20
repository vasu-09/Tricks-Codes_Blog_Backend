package com.tandc.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tandc.blog.dto.CommentDto;
import com.tandc.blog.dto.Commentget;
import com.tandc.blog.service.CommentService;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("comments")
@Transactional
public class CommentsController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/all/{id}")
	public ResponseEntity<List<Commentget>> getComments(@PathVariable Long id){
		return commentService.getCommentsByArticleId(id);
	}
	
	@PostMapping("add/{id}")
	public ResponseEntity<Commentget> addComment(@RequestBody CommentDto commentDto, @PathVariable Long id){
		return commentService.addComment(commentDto, id);
	}
	
	@PostMapping("put/{id}")
	public ResponseEntity<Commentget> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long id){
		return commentService.updateComment(commentDto, id);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Long id){
		return commentService.deleteComment(id);
	}
}
