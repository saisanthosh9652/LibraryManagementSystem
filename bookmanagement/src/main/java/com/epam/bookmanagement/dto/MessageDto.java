package com.epam.bookmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto{
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
