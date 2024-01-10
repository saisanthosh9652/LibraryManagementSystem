package com.epam.bookmanagement.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.bookmanagement.dto.ErrorDto;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;

@RestControllerAdvice
public class BookExceptionHandler {
	private ErrorDto errorDto = new ErrorDto();

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto bookAlreadyPresentExceptionHandler(BookAlreadyPresentException e) {
		errorDto.setError("The book is already present");
		return errorDto;
	}
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	ErrorDto noBookFoundExceptionHandler(NoBookFoundException e) {
		errorDto.setError("There is no book with the particular ID");
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
