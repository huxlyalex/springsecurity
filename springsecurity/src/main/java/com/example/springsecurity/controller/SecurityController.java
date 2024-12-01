package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.entity.Student;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/student")
public class SecurityController {
	
	
	List<Student> studentList = new ArrayList<>(List.of(
			new Student(1,"huxly","alex"),
			new Student(2,"alex","ninan")
			
			));
	
	
	@GetMapping("/sessionid")
	public String getName(HttpServletRequest request)
	{
		return "welcome to Spring Security"+request.getSession().getId();
	}
	
	@GetMapping("/getstudent")
	public List<Student> getStudentList ()
	{
		return studentList;
	}
	
	
	@GetMapping("/csrf")
	public CsrfToken getCrstToken(HttpServletRequest request)
	{
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	@PostMapping("/addstudent")
	public Student addStudent(@RequestBody Student stu)
	{
		System.out.println(stu);
		studentList.add(stu);
		return stu;
	}

}
