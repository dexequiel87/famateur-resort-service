package com.degg.famateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/api-docs")
    public String getApiDocs() {
        return "forward:docs/index.html";
    }
}
