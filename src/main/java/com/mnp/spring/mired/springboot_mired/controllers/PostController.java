package com.mnp.spring.mired.springboot_mired.controllers;

import com.mnp.spring.mired.springboot_mired.dto.PostDTO;
import com.mnp.spring.mired.springboot_mired.dto.PostResponseDTO; // Importa el DTO
import com.mnp.spring.mired.springboot_mired.models.Post;
import com.mnp.spring.mired.springboot_mired.models.Users;
import com.mnp.spring.mired.springboot_mired.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // CREATE: Crear una nueva publicación
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDTO postDTO, 
                                        BindingResult result,
                                        @AuthenticationPrincipal Users currentUser) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        // Asegurarse de que el postDTO tenga el ID del usuario autenticado
        //postDTO.setUserId(currentUser.getId());  // 

        Post createdPost = postService.createPost(postDTO);

        // Convertir la entidad Post a PostResponseDTO
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setId(createdPost.getId());
        postResponseDTO.setTitle(createdPost.getTitle());
        postResponseDTO.setContent(createdPost.getContent());
        postResponseDTO.setAuthor(createdPost.getAuthor());
        postResponseDTO.setYear(createdPost.getYear());
        postResponseDTO.setCreatedAt(createdPost.getCreatedAt());
        postResponseDTO.setUpdatedAt(createdPost.getUpdatedAt());
        postResponseDTO.setUserEmail(currentUser.getEmail()); // Asignar el email del usuario autenticado

        return ResponseEntity.status(201).body(postResponseDTO);
    }

    // READ: Obtener todas las publicaciones
    @GetMapping("/")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();

        // Convertir cada Post a PostResponseDTO
        List<PostResponseDTO> postResponseDTOs = posts.stream().map(post -> {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setAuthor(post.getAuthor());
            dto.setYear(post.getYear());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUpdatedAt(post.getUpdatedAt());
            dto.setUserEmail(post.getUser().getEmail()); // Asignar el email del usuario
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(postResponseDTOs);
    }

    // READ: Obtener todas las publicaciones de un usuario autenticado
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(@PathVariable Long userId) {
    List<Post> posts = postService.getPostsByUserId(userId);

    List<PostResponseDTO> postResponseDTOs = posts.stream().map(post -> {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        dto.setYear(post.getYear());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setUserEmail(post.getUser().getEmail()); // Asignar el email del usuario
        return dto;
    }).collect(Collectors.toList());

        return posts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(postResponseDTOs);
    }

    @GetMapping("/author/{author}/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByAuthorAndUser(
            @PathVariable String author, @PathVariable Long userId) {

        // Obtener las publicaciones filtradas por autor y userId
        List<Post> posts = postService.getPostsByAuthorAndUser(author, userId);

        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Convertir cada Post a PostResponseDTO
        List<PostResponseDTO> postResponseDTOs = posts.stream().map(post -> {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setAuthor(post.getAuthor());
            dto.setYear(post.getYear());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUpdatedAt(post.getUpdatedAt());
            dto.setUserEmail(post.getUser().getEmail()); // Asignar el email del usuario
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(postResponseDTOs);
    }


    // READ: Ver detalle de una publicación
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);

        if (post.isPresent()) {
            Post postEntity = post.get();

            // Convertir Post a PostResponseDTO
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.setId(postEntity.getId());
            postResponseDTO.setTitle(postEntity.getTitle());
            postResponseDTO.setContent(postEntity.getContent());
            postResponseDTO.setAuthor(postEntity.getAuthor());
            postResponseDTO.setYear(postEntity.getYear());
            postResponseDTO.setCreatedAt(postEntity.getCreatedAt());
            postResponseDTO.setUpdatedAt(postEntity.getUpdatedAt());
            postResponseDTO.setUserEmail(postEntity.getUser().getEmail()); // Asignar el email del usuario

            return ResponseEntity.ok(postResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE: Actualizar una publicación
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Post updatedPost = postService.updatePost(id, postDTO);

            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.setId(updatedPost.getId());
            postResponseDTO.setTitle(updatedPost.getTitle());
            postResponseDTO.setContent(updatedPost.getContent());
            postResponseDTO.setAuthor(updatedPost.getAuthor());
            postResponseDTO.setYear(updatedPost.getYear());
            postResponseDTO.setCreatedAt(updatedPost.getCreatedAt());
            postResponseDTO.setUpdatedAt(updatedPost.getUpdatedAt());
            postResponseDTO.setUserEmail(updatedPost.getUser().getEmail());

            return ResponseEntity.ok(postResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // DELETE: Eliminar una publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return postService.deletePost(id) ? 
            ResponseEntity.ok(Map.of("message", "Publicación eliminada exitosamente")) :
            ResponseEntity.notFound().build();
    }
}
