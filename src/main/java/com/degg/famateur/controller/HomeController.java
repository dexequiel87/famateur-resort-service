package com.degg.famateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    public static final String DOCS_INDEX_HTML = "docs/index.html";

    @GetMapping("/")
    public String getApiDocs() {
        return DOCS_INDEX_HTML;
    }
}
