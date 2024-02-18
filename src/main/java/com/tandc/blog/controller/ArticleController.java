package com.tandc.blog.controller;

import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tandc.blog.dto.ArticleRequest;
import com.tandc.blog.dto.Articledto;
import com.tandc.blog.service.ArticleService;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	
	
	@PostMapping(value = "post/{cat_id}")
	public ResponseEntity<String> addArt(@RequestParam("title") String title,
	        @RequestParam("content") String content,
	         @PathVariable Long cat_id) throws IOException{
	    try {
	        ArticleRequest articleRequest = new ArticleRequest();
	        articleRequest.setContent(content);
	        articleRequest.setImages(null);
	        articleRequest.setTitle(title);

	        return new ResponseEntity<>( articleService.addArticle(articleRequest, cat_id), HttpStatus.CREATED);
	    } catch (Exception e) {
	        e.printStackTrace();  // Log the exception
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("get")
	public ResponseEntity<List<Articledto>> getallArticles(){
		return articleService.getallArticles();
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<Articledto> getArticle(@PathVariable Long id){
		return articleService.getArticle(id);
	}
	
	@PutMapping("put/{id}")
	public ResponseEntity<Articledto> updateArticle(@RequestParam("title") String title,
	        @RequestParam("content") String content,
	        @PathVariable Long id) throws IOException{
	    try {
	        ArticleRequest articleRequest = new ArticleRequest();
	        articleRequest.setContent(content);
	        articleRequest.setImages(null);
	        articleRequest.setTitle(title);
		return articleService.updateArticle(id, articleRequest);
	    }catch (Exception e) {
	    	 e.printStackTrace();  // Log the exception
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteArticle(@PathVariable Long id){
		return articleService.deleteArticle(id);
	}
	
}
