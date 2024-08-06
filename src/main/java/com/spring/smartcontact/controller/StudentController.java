package com.spring.smartcontact.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.smartcontact.entity.Contact;
import com.spring.smartcontact.entity.Student;
import com.spring.smartcontact.helper.Message;
import com.spring.smartcontact.repo.ContactRepo;
import com.spring.smartcontact.repo.StudentRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private ContactRepo contactRepo;

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

	// handler for view contacts
	// and show contacts per page
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {

		model.addAttribute("title", "show user contacts");

		String userName = principal.getName();
		Student student = this.studentRepo.getStudentByUserName(userName);

		Pageable pageable = PageRequest.of(page, 3);

		Page<Contact> contacts = this.contactRepo.findContactByUser(student.getId(), pageable);

		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);

		model.addAttribute("totalpages", contacts.getTotalPages());

		return "normal/show_contacts";
	}

	// showing particular contact details
	@GetMapping("/contact/{cId}")
	public String showContactDetails(@PathVariable("cId") Integer cId, Model model, Principal principal) {

		Optional<Contact> contactOptional = this.contactRepo.findById(cId);
		Contact contact = contactOptional.get();

		//
		String userName = principal.getName();
		Student student = this.studentRepo.getStudentByUserName(userName);

		if (student.getId() == contact.getStudent().getId()) {
			model.addAttribute("contact", contact);
		}

		return "normal/contact_details";
	}

	// delete contact
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, HttpSession session) {

		Contact contact = this.contactRepo.findById(cId).get();

		// check
		this.contactRepo.delete(contact);
		
		session.setAttribute("message", new Message("contact deleted successfully!!", "success"));

		return "redirect:/student/show-contacts/0";
	}
	
	// show update contact
	@PostMapping("/updateContact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId,Model model) {
		
		Contact contact = this.contactRepo.findById(cId).get();
		model.addAttribute(contact);
		
		return "normal/update_contact_form";
	}
	
	//update contact
	@PostMapping("/proccess-update")
	public String updateHandler(@ModelAttribute Contact contact, HttpSession session, Principal principal) {
		
		try {
			Student student= this.studentRepo.getStudentByUserName(principal.getName());
			contact.setStudent(student);
			this.contactRepo.save(contact);
			session.setAttribute("message", new Message("contact updated successfully!!!","success"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/student/contact/"+contact.getCId();    
	}
}
