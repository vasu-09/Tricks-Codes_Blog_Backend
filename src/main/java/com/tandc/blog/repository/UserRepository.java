package com.tandc.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tandc.blog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsernameOrEmail(String username, String email);
	User findByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
	Optional<User> findByResetToken(String resetToken);
	Optional<User> findByEmail(String userEmail);

}
