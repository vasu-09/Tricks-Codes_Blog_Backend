package com.tandc.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tandc.blog.dto.CatArtdto;
import com.tandc.blog.dto.Categorydto;
import com.tandc.blog.entity.Category;
import com.tandc.blog.repository.CategoryRepository;

@Service
public class CategorySevice {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public ResponseEntity<List<Categorydto>> getAllCat() {
		
		List<Category> categorydto = categoryRepository.findAll();
		
		return new ResponseEntity<>(categorydto.stream().map(this::convCategorydto).collect(Collectors.toList()), HttpStatus.OK);
	}

	public ResponseEntity<String> addCat(Category category) {
		
//		Category category = new Category();
//		category.setName(name);
		
		categoryRepository.save(category);
		return ResponseEntity.ok("Added successfully");
	}
	
	public Categorydto convCategorydto(Category category) {
		Categorydto categorydto = new Categorydto();
		
		categorydto.setId(category.getId());
		categorydto.setName(category.getName());
		
		return categorydto;
	}

	public ResponseEntity<List<CatArtdto>> getallCatArt() {
		List<Category> categories = categoryRepository.findAll();
		return new ResponseEntity<>(categories.stream().map(this::convertToDTO).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	public CatArtdto convertToDTO(Category category) {
		
		CatArtdto catArtdto = new CatArtdto(category);
		
		return catArtdto;
	}

	public ResponseEntity<String> deleteCategory(Long id) {
		categoryRepository.deleteById(id);
		return ResponseEntity.ok("Category delete");
	}

}
