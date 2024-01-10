package com.epam.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
	String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
