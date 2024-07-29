package com.spring.smartcontact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.smartcontact.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{

	@Query("select s from Student s where s.email = :email")
	public Student getStudentByUserName(@Param("email") String email);
}
