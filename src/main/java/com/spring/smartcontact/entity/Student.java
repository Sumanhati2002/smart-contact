package com.spring.smartcontact.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min = 3, max = 40)
	@NotEmpty(message = "Username cannot be empty")
	private String name;
	
	@Email(message = "Please enter a valid e-mail address")
	@NotBlank(message = "e-mail address should not be null")
	private String email;
	
	@NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be between 4 and 15 characters")
	private String password;
	
	private String image;
	
	@Size(max = 100)
	private String about;
	
	private String role;
	
	@Column(length = 500)
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private List<Contact> contacts= new ArrayList<>();
}
