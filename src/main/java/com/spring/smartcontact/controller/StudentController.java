package com.spring.smartcontact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.smartcontact.entity.Contact;
import com.spring.smartcontact.entity.Student;
import com.spring.smartcontact.helper.Message;
import com.spring.smartcontact.repo.StudentRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("username" + userName);

		Student student = studentRepo.getStudentByUserName(userName);
		System.out.println(student);

		model.addAttribute(student);
	}

	@GetMapping("/index")
	public String dashBoard(Model model, Principal principal) {

		return "normal/student_dashboard";
	}

	// handler for add contact
	@GetMapping("/add-contact")
	public String openContactForm(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}

	// handler for save student
	@PostMapping("/proccess-contact")
	public String proccessContact(@ModelAttribute Contact contact, Principal principal, HttpSession session) {

		try {
			String name = principal.getName();
			Student student = this.studentRepo.getStudentByUserName(name);

			student.getContacts().add(contact);
			contact.setStudent(student);

			this.studentRepo.save(student);

			System.out.println(contact);
			System.out.println("added to database.....");
			session.setAttribute("message", new Message("your contact has been added!!!", "success"));
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("something went wrong try again", "danger"));
		}
		return "normal/add_contact_form";
	}
}
