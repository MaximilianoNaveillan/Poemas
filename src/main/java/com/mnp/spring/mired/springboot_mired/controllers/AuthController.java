package com.mnp.spring.mired.springboot_mired.controllers;

import com.mnp.spring.mired.springboot_mired.dto.UserLoginDTO;
import com.mnp.spring.mired.springboot_mired.dto.UserLoginResponseDTO;
import com.mnp.spring.mired.springboot_mired.dto.UserRegisterDTO;
import com.mnp.spring.mired.springboot_mired.models.Users;
import com.mnp.spring.mired.springboot_mired.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;  // Asegúrate de usar 'jakarta.validation.Valid'
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO userDTO, BindingResult result) {
        // Validación de errores
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        // Verificar si el correo ya existe
        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "El correo electrónico ya está registrado"));
        }

        // Convertir DTO a modelo Users
        //Users user = new Users(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstname(), userDTO.getLastname());
        
        // Cambiar 'registUsers' a 'registerUser'
        userService.registerUser(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstname(), userDTO.getLastname());

        return ResponseEntity.status(201).body(Map.of("message", "Usuario registrado exitosamente"));
    }

    // Endpoint para login de usuario
    // AuthController.java
    // AuthController.java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
    // Cambiar el nombre del método a authenticateUser
    Optional<Users> userOptional = userService.authenticateUser(loginDTO.getEmail(), loginDTO.getPassword());

    if (userOptional.isPresent()) {
        Users user = userOptional.get();
        
        // Convertir el usuario a un DTO de respuesta
        UserLoginResponseDTO userResponse = new UserLoginResponseDTO(
            user.getId(),
            user.getEmail(),
            user.getFirstname(),
            user.getLastname()
        );
        
        // Responder con los datos del usuario filtrados
        return ResponseEntity.ok(Map.of("message", "Login exitoso", "user", userResponse));
    } else {
        return ResponseEntity.status(401).body(Map.of("message", "Credenciales incorrectas"));
    }
}
}
