package com.mnp.spring.mired.springboot_mired.services;

import com.mnp.spring.mired.springboot_mired.dto.PostDTO;
import com.mnp.spring.mired.springboot_mired.models.Post;
import com.mnp.spring.mired.springboot_mired.models.Users;
import com.mnp.spring.mired.springboot_mired.repository.PostRepository;
import com.mnp.spring.mired.springboot_mired.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // Inyección de UserRepository

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;  // Inyectamos el UserRepository
    }

    // Crear un nuevo post
    public Post createPost(PostDTO postDTO) {
        // Buscar el usuario por ID (asegúrate de que userId sea de tipo Long)
        Optional<Users> user = userRepository.findById(postDTO.getUserId());  // Aquí postDTO.getUserId() debe ser Long
        if (user.isEmpty()) {
            throw new IllegalArgumentException("El usuario no existe");
        }
    
        // Crear el post y asignar el usuario
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(postDTO.getAuthor());
        post.setYear(postDTO.getYear());
        post.setUser(user.get());  // Asignar el usuario encontrado
    
        // Guardar el post
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

    // Obtener todos los posts de un usuario específico
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);  // Filtrar publicaciones por userId
    }

    // Obtener publicaciones por autor y userId
    public List<Post> getPostsByAuthorAndUser(String author, Long userId) {
        return postRepository.findByAuthorAndUserId(author, userId);  // Filtrar publicaciones por autor y userId
    }

    // Actualizar un post
    public Post updatePost(Long id, PostDTO postDTO) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isEmpty()) {
            throw new IllegalArgumentException("Post no encontrado");
        }
        
        Optional<Users> user = userRepository.findById(postDTO.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    
        Post post = existingPost.get();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(postDTO.getAuthor());
        post.setYear(postDTO.getYear());
        post.setUser(user.get());
    
        return postRepository.save(post);
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
