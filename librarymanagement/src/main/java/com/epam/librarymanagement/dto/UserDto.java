package com.epam.librarymanagement.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDto {
	String username;
	String email;
	String name;
	@JsonInclude(Include.NON_NULL)
	List<BookDto> booksList;
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
	public List<BookDto> getBooksList() {
		return booksList;
	}
	public void setBooksList(List<BookDto> booksList) {
		this.booksList = booksList;
	}
}
