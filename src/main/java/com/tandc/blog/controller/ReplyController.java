package com.tandc.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tandc.blog.dto.CommentDto;
import com.tandc.blog.dto.Commentget;
import com.tandc.blog.service.ReplyService;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/replies")
public class ReplyController {
    // Endpoint to add a reply to a comment
	@Autowired
	private ReplyService replyService;
	
    @PostMapping("/add/{commentId}")
    public ResponseEntity<Commentget> addReply(@RequestBody CommentDto replyDto,@PathVariable Long commentId) {
        // Add logic to handle adding a reply
    	return replyService.addReply(replyDto, commentId);
    }

    // Endpoint to get replies for a comment
    @GetMapping("/get/{commentId}")
    public ResponseEntity<List<Commentget>> getReplies(@PathVariable Long commentId) {
        // Add logic to retrieve replies for a comment
    	return replyService.getReplies(commentId);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteReply(@PathVariable Long id){
    	return replyService.deleteReply(id);
    }
    
}

