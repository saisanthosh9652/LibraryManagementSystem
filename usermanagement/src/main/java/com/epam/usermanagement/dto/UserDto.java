package com.epam.usermanagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	@NotNull(message="username cannot be null")
	@Size(min=3,max=10,message="username is not proper")
	String username;
	String email;
	@Size(min=3,max=10,message="name is not proper")
	String name;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
