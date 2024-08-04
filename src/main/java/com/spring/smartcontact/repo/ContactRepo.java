package com.spring.smartcontact.repo;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.smartcontact.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	//pagination.....
	@Query("from Contact as c where c.student.id = :id")
	public Page<Contact> findContactByUser(@Param("id") Integer id,Pageable pageable);
	
	//pageable has two information
	//one is current page i.e page
	//another contact per page

}
