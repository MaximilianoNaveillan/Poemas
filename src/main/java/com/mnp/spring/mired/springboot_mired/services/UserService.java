package com.mnp.spring.mired.springboot_mired.services;

import com.mnp.spring.mired.springboot_mired.models.Users;
import com.mnp.spring.mired.springboot_mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Registrar nuevo usuario
    public Users registUsers(Users user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));  // Cifrar contraseña
        return userRepository.save(user);
    }

    // Autenticar al usuario
    public Optional<Users> authenticateUsers(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Verificar si el email ya está registrado
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
