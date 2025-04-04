package com.mnp.spring.mired.springboot_mired.controllers;

import com.mnp.spring.mired.springboot_mired.dto.PostDTO;
import com.mnp.spring.mired.springboot_mired.models.Post;
import com.mnp.spring.mired.springboot_mired.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // CREATE: Crear una nueva publicación
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDTO postDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImage(postDTO.getImage());  // Asignar imagen
        post.setCategory(postDTO.getCategory());  // Asignar categoría

        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(201).body(createdPost);
    }

    // READ: Obtener todas las publicaciones
    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // READ: Ver detalle de una publicación
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE: Actualizar una publicación
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImage(postDTO.getImage());  // Asignar imagen
        post.setCategory(postDTO.getCategory());  // Asignar categoría

        Post updatedPost = postService.updatePost(id, post);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    // DELETE: Eliminar una publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return postService.deletePost(id) ? 
            ResponseEntity.ok(Map.of("message", "Publicación eliminada exitosamente")) :
            ResponseEntity.notFound().build();
    }
}
