package com.epam.librarymanagement.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;
import com.epam.librarymanagement.service.ILibraryService;

import io.swagger.annotations.ApiOperation;

@RestController
public class LibraryRestController {
	@Autowired
	ILibraryService libraryService;

	private MessageDto message = new MessageDto();
	private static final String ASSIGN_SUCCESS = "Book the assigned to the user";
	private static final String DELETE_SUCCESS = "Book is un-assigned to the user";

	@PostMapping("library/users/{username}/books/{bookId}")
	@ApiOperation("assign's book to the user")
	public ResponseEntity<MessageDto> assignBookToUser(@PathVariable(name = "username") String username,
			@PathVariable(name = "bookId") int bookId) throws BookAlreadyAssignedToUserException {

		libraryService.assignBookToUser(username, bookId);
		message.setMessage(ASSIGN_SUCCESS);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@DeleteMapping("library/users/{username}/books/{bookId}")
	@ApiOperation("release the book assign to user")
	public ResponseEntity<MessageDto> deleteAssignedBookToUser(@PathVariable(name = "username") String username,
			@PathVariable(name = "bookId") int bookId) throws BookNotAssignedToUserException {
		libraryService.removeAssignedBookFromUser(username, bookId);
		message.setMessage(DELETE_SUCCESS);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
