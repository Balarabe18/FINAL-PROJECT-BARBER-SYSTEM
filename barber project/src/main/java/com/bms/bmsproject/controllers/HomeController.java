package com.bms.bmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bms.bmsproject.services.JokeService;

@Controller
public class HomeController {

    @Autowired
    JokeService jokeService;

    @GetMapping("/")
    public String index(Model model) {
        // Return the name of the Thymeleaf template (index.html)
        try {
            model.addAttribute("jokes", jokeService.getJokes());
            return "index";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "index";
        }
    }
}
