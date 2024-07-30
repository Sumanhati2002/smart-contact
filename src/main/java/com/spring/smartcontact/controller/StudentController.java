package com.spring.smartcontact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.smartcontact.entity.Student;
import com.spring.smartcontact.repo.StudentRepo;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;
	
	@GetMapping("/index")
	public String dashBoard(Model model, Principal principal) {
		
		String userName=principal.getName();
		
		Student student=studentRepo.getStudentByUserName(userName);
		System.out.println(student);
		
		model.addAttribute(student);
		
		return "normal/student_dashboard";
	}
	
	
}
