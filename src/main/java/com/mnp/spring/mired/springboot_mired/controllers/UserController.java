package com.mnp.spring.mired.springboot_mired.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mnp.spring.mired.springboot_mired.models.Users;


@Controller
public class UserController {
    @GetMapping("/user_info")

    public String info(Model model){
        Users user = new Users("max@gmail.com","123","Maximiliano","Naveillan",1);
        model.addAttribute("Usuario", user);
        return "user_info";
    }
    
}
