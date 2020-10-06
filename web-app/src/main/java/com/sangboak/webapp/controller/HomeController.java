package com.sangboak.webapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log
@Controller
@AllArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        log.info("called");
        return "index";
    }
}