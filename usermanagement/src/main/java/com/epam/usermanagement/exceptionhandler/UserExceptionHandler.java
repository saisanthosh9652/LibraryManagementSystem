package com.epam.usermanagement.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.usermanagement.dto.ErrorDto;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;

@RestControllerAdvice
public class UserExceptionHandler {
	private ErrorDto errorDto = new ErrorDto();

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
		errorDto.setError("The user is already present");
		return errorDto;
	}
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto userDoesNotExistsExceptionHandler(UserDoesNotExistsException e) {
		errorDto.setError("There is no user with the particular username");
		return errorDto;
	}
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		List<String> errorList = new ArrayList<>();
		e.getAllErrors().forEach(error->errorList.add(error.getDefaultMessage()));
		errorDto.setError(errorList.toString());
		return errorDto;
	}
}
