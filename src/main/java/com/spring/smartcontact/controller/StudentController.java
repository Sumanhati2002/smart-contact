package com.spring.smartcontact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

	@GetMapping("/index")
	public String dashBoard() {
		
		return "normal/student_dashboard";
	}
}
