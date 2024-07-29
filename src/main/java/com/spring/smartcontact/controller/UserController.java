package com.spring.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.smartcontact.entity.Student;
import com.spring.smartcontact.helper.Message;
import com.spring.smartcontact.repo.StudentRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private StudentRepo userRepo;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("student", new Student());
		return "signup";
	}

//	handler for register user
	@Transactional
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			@RequestParam(value = "aggrement", defaultValue = "false") boolean aggrement, HttpSession session) {
		try {
			if (!aggrement) {
				throw new Exception("you did not check terms and conditions");
			}
			if (bindingResult.hasErrors()) {
				return "signup";
			}
			
			student.setRole("ROLE_USER");
			student.setEnabled(true);
			student.setImage("default.png");
			
			String encodedPassword = passwordEncoder.encode(student.getPassword());
			student.setPassword(encodedPassword);

			this.userRepo.save(student);

			/* model.addAttribute("student", new Student()); */

			session.setAttribute("message", new Message("Student registered successfully!!!!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			// model.addAttribute("student", student);
			session.setAttribute("message", new Message("Something went wrong!! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
	
	//login handler
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
}
