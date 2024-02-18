package com.tandc.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tandc.blog.dto.ArticleRequest;
import com.tandc.blog.dto.Articledto;
import com.tandc.blog.entity.Article;
import com.tandc.blog.entity.Category;
import com.tandc.blog.repository.ArticleRepository;
import com.tandc.blog.repository.CategoryRepository; 

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArticleService {
	

	
   // Define a property in application.properties for the upload path
    private static String uploadPath = System.getProperty("user.dir")+"/src/main/webapps/images/";
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ArticleRepository articleRepository;

    // Autowire ArticleRepository and other dependencies

    public String addArticle(ArticleRequest articleRequest, Long id) {
    	try {
    		Category category = categoryRepository.findById(id).get();
        	
        	Article article = new Article();
        	
        	article.setCategory(category);
        	article.setContent(articleRequest.getContent());
        	article.setTitle(articleRequest.getTitle());
        	article.setCreatedDate(LocalDateTime.now());
        	
            List<String> imageUrls = saveImages(articleRequest.getImages());
            article.setImages(imageUrls);

            articleRepository.save(article);
            return "article added successfully";
		} catch (Exception e) {
			return null;
		}
    	
    }

    private List<String> saveImages(List<MultipartFile> images) {
        
    	try {
    		List<String> imageUrls = new ArrayList<>();
    		 

            for (MultipartFile image : images) {
                // Generate a unique filename
                String fileName = image.getOriginalFilename();

                // Build the file path using the dynamic uploadPath
                String filePath = uploadPath+fileName;
                image.transferTo(new File(filePath));
                imageUrls.add(filePath);
     
            }

            return imageUrls;
		} catch (Exception e) {
			return null;
		}
    }

	public ResponseEntity<List<Articledto>> getallArticles() {
		List<Article> article = articleRepository.findByOrderByCreatedDateDesc();
		return new ResponseEntity<>(article.stream().map(this::convertToDTO).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	private Articledto convertToDTO(Article article) {
        if (article != null) {
            Articledto dto = new Articledto();
            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            dto.setContent(article.getContent());
            dto.setImages(article.getImages());
            dto.setCreatedDate(article.getCreatedDate());
            dto.setCategoryid(article.getCategoryId());
            return dto;
        }
        return null;
    }

	public ResponseEntity<Articledto> getArticle(Long id) {
		
		Article article = articleRepository.findById(id).get();
		Articledto articledto = convertToDTO(article);
		
		return new ResponseEntity<>(articledto, HttpStatus.OK);
	}

	public ResponseEntity<String> saveArticle(ArticleRequest articledto, Long cat_id) {
		
		Category category = categoryRepository.findById(cat_id).get();
    	
    	Article article = new Article();
    	
    	article.setCategory(category);
    	article.setContent(articledto.getContent());
    	article.setTitle(articledto.getTitle());
    	
    	List<String> imageUrls = saveImages(articledto.getImages());
        article.setImages(imageUrls);
    	
    	
    	articleRepository.save(article);
		
		return new ResponseEntity<>("article saved successfully", HttpStatus.CREATED);
	}



	public ResponseEntity<Articledto> updateArticle(Long id, ArticleRequest articleRequest) {
		if(articleRepository.existsById(id)) {
			Article article = articleRepository.findById(id).get();
	
			article.setContent(articleRequest.getContent());
			article.setTitle(articleRequest.getTitle());
			article.setCreatedDate(LocalDateTime.now());
	
			List<String> imageUrls = saveImages(articleRequest.getImages());
			article.setImages(imageUrls);
			

			articleRepository.save(article);
			Articledto articledto = convertToDTO(article);
			return new ResponseEntity<>(articledto, HttpStatus.CREATED);
		} 
		return null;
	}

	public ResponseEntity<String> deleteArticle(Long id) {
		articleRepository.deleteById(id);
		
		return new ResponseEntity<>("Article deleted Successfully!", HttpStatus.OK);
	}

}

