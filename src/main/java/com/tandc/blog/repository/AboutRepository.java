package com.tandc.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tandc.blog.entity.About;

@Repository
public interface AboutRepository extends JpaRepository<About, Long>{

}
