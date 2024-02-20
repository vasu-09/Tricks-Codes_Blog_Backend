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

import com.tandc.blog.dto.CatArtdto;
import com.tandc.blog.dto.Categorydto;
import com.tandc.blog.entity.Category;
import com.tandc.blog.service.CategorySevice;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategorySevice categoryService;
	
	@GetMapping("all")
	public ResponseEntity<List<Categorydto>> getAllCat(){
		return categoryService.getAllCat();
	}
	
	@PostMapping("add")
	public String addCat(@RequestBody Category category){
		try {
	        // Your service logic here
	        categoryService.addCat(category);
	        return "Category added successfully";
	    } catch (Exception e) {
	        return "Error adding category";
	    }
	}
	
	@GetMapping("art/all")
	public ResponseEntity<List<CatArtdto>> getallCatArt(){
		return categoryService.getallCatArt();
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		return categoryService.deleteCategory(id);
	}
	
}
