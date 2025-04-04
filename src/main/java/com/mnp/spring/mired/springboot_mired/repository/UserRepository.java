package com.mnp.spring.mired.springboot_mired.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mnp.spring.mired.springboot_mired.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByEmail(String email);    
} 