package com.tandc.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tandc.blog.entity.About;
import com.tandc.blog.repository.AboutRepository;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping
public class AboutController {
	
	@Autowired
	private AboutRepository aboutRepository;
	
	@GetMapping("about")
	public ResponseEntity<About> getAbout(){
		return new ResponseEntity<>(aboutRepository.findById((long) 1).get(), HttpStatus.OK);
	}
	
	@PutMapping("update/about")
	public ResponseEntity<About> updateAbout(@RequestBody About content){
		About about = aboutRepository.findById((long) 1).get();
		about.setContent(content.getContent());
		aboutRepository.save(about);
		return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
	}

}
