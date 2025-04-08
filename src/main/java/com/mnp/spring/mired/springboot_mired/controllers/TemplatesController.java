package com.mnp.spring.mired.springboot_mired.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TemplatesController {
    @GetMapping("/")
    public String index() {
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/addpost")
    public String addpost() {
        return "addpost";
    }

    @GetMapping("/viewpost")
    public String viewpost() {
        return "viewpost";
    }
    
    
}
