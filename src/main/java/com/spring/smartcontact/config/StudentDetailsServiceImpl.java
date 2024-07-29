package com.spring.smartcontact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.smartcontact.entity.Student;
import com.spring.smartcontact.repo.StudentRepo;

public class StudentDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private StudentRepo studentRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetch student from database
		Student student= studentRepo.getStudentByUserName(username);
		if (student==null) {
			
			throw new UsernameNotFoundException("could not found student");
		}
		CustomStudentDetails customStudentDetails= new CustomStudentDetails(student);
		
		return customStudentDetails;
	}

}
