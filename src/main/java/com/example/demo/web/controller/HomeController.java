package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	@RequestMapping("/body")
	public String home() {
		return "home/body";
	}
	
}
