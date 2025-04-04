package com.mnp.spring.mired.springboot_mired.repository;

import com.mnp.spring.mired.springboot_mired.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Aquí podemos agregar métodos personalizados si es necesario en el futuro
}
