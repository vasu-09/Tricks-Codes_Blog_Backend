package com.tandc.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tandc.blog.entity.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	boolean existsById(Long id);
	Article findByTitle(String title);
	List<Article> findByOrderByCreatedDateDesc();
}
