package com.mnp.spring.mired.springboot_mired.services;


import com.mnp.spring.mired.springboot_mired.models.Users;
import com.mnp.spring.mired.springboot_mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // Registrar nuevo usuario
    @Transactional
    public Users registerUser(String email, String password, String firstname, String lastname) {
        // Verificar si el correo ya está registrado
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Si el correo no existe, crear el nuevo usuario con la contraseña cifrada
        String encodedPassword = encoder.encode(password);
        Users user = new Users(email, encodedPassword, firstname, lastname);
        return userRepository.save(user);
    }

    // Autenticar al usuario
    public Optional<Users> authenticateUser(String email, String password) {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (encoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Verificar si el correo ya está registrado
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
