package com.tandc.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tandc.blog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
