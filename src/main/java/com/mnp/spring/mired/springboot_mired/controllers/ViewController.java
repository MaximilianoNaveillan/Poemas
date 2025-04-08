package com.mnp.spring.mired.springboot_mired.controllers;

import com.mnp.spring.mired.springboot_mired.models.Post;
import com.mnp.spring.mired.springboot_mired.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ViewController {

    private final PostService postService;

    @Autowired
    public ViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/allpoems/{author}/user/{userId}")
    public String getPoemsByAuthorAndUser(@PathVariable String author,
                                          @PathVariable Long userId,
                                          Model model) {
        List<Post> poems = postService.getPostsByAuthorAndUser(author, userId);
        model.addAttribute("poems", poems); // pasamos la lista a la vista
        return "allpoems"; // renderiza allpoems.html
    }
}
