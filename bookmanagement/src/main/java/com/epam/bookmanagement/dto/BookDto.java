package com.epam.bookmanagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto{
	@NotNull(message="name cannot be null")
	@Size(min=3,max = 20 , message="Name is Not proper")
	String name;
	@Size(min=3,max = 20 , message="publisher name is Not proper")
	String publisher;
	@Size(min=3,max = 20 , message="author name is Not proper")
	String author;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}
