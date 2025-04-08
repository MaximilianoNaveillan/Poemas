package com.mnp.spring.mired.springboot_mired.repository;

import com.mnp.spring.mired.springboot_mired.models.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId); 
    List<Post> findByAuthorAndUserId(String author, Long userId);
}
