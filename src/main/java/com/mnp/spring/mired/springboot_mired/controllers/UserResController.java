package com.mnp.spring.mired.springboot_mired.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.mnp.spring.mired.springboot_mired.models.Users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")

public class UserResController {
    
    @GetMapping(path = "/user_info")

    public Map<String, Object> detalles_user() {
        Users user = new Users("max@gmail.com","123","Maximiliano","Naveillan",1);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Usuario",user);
        return respuesta;
    }
    

}
