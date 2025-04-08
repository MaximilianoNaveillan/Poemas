package com.mnp.spring.mired.springboot_mired.repository;

import com.mnp.spring.mired.springboot_mired.models.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> { 
    Optional<Users> findByEmail(String email);  // Buscar por correo electrónico
    boolean existsByEmail(String email);  // Verificar si el correo ya existe
}