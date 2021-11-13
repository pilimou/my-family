package com.example.demo.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class SystemController {
	
	@GetMapping("/sys/sign-in")
	public String login(HttpSession session) {
		String error = (String) session.getAttribute("error");
		if(null != error) {
			
		} else {
			session.setAttribute("error","");
			session.removeAttribute("error");
		}
		return "indexLogin";
	}
	
	@GetMapping("/sys/wake_up")
	@ResponseBody
	public String wake_up() {
		return "still alive";
	}
	
	@GetMapping("/favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }
	
}
