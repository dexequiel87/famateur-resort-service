package com.degg.famateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControlPanelController {

	private static final String CONTROL_PANEL = "control-panel";

	@GetMapping("/control-panel")
	public String account() {
		return CONTROL_PANEL;
	}
}
