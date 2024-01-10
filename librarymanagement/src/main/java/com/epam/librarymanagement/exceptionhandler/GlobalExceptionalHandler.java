package com.epam.librarymanagement.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.epam.librarymanagement.dto.ErrorDto;
import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionalHandler {
	private ObjectMapper mapper = new ObjectMapper();
	private ErrorDto error = new ErrorDto();

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto httpClientErrorExceptionHandler(HttpClientErrorException e)
			throws  JsonProcessingException {
		return mapper.readValue(e.getResponseBodyAsString(), ErrorDto.class);
	}
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto feignExceptionHandler(FeignException e)
			throws JsonProcessingException {
		return mapper.readValue(e.contentUTF8(), ErrorDto.class);
	}
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto bookAlreadyAssignedToUserExceptionHandler(BookAlreadyAssignedToUserException e) {
		error.setError("The particular book is already assigned to user");
		return error;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto bookNotAssignedToUserExceptionHandler(BookNotAssignedToUserException e) {
		error.setError("The particular book is not assigned to user to release");
		return error;
	}
}
