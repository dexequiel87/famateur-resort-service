package com.degg.famateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

	private static final String ACCOUNT = "account";

	@GetMapping("/account")
	public String account() {
		return ACCOUNT;
	}
}
