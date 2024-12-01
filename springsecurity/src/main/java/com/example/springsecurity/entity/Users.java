package com.example.springsecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Users {
	@Id
	Integer id;
	String firstName;
	String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users() {
	}

	public Users(Integer id, String firstName, String password) {
		this.id = id;
		this.firstName = firstName;
		this.password = password;
	}
}
