package com.epam.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto{

	String name;
	String publisher;
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
