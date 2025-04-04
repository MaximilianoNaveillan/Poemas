package com.mnp.spring.mired.springboot_mired.services;

import com.mnp.spring.mired.springboot_mired.models.Post;
import com.mnp.spring.mired.springboot_mired.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;  // Importa Optional

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Crear un nuevo post
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // Obtener todos los posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Obtener un post por su ID
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // Actualizar un post
    public Post updatePost(Long id, Post post) {
        if (postRepository.existsById(id)) {
            post.setId(id);
            return postRepository.save(post);
        }
        return null;
    }

    // Eliminar un post
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
