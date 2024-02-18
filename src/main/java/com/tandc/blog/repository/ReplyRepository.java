package com.tandc.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandc.blog.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByCommentIdOrderByCreatedAtDesc(Long id);
	void deleteByCommentId(Long id);
	boolean existsByCommentId(Long id);
}
