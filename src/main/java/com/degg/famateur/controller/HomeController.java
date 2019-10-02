package com.degg.famateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final String HOME = "home";

	@GetMapping("/")
	public String home() {
		return HOME;
	}
}
