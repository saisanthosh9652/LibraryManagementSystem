package com.epam.librarymanagement.restcontrollers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.librarymanagement.dto.ErrorDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;
import com.epam.librarymanagement.service.ILibraryBookService;
import com.epam.librarymanagement.service.ILibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
class LibraryRestControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	ILibraryService service;
	@MockBean
	ILibraryBookService bookService;
	private MessageDto message = new MessageDto();
	private ErrorDto error = new ErrorDto();
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void assignTest() throws Exception {
		message.setMessage("Book the assigned to the user");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(service).assignBookToUser("reshma98", 10);
		mvc.perform(post("/library/users/reshma98/books/10")).andExpect(status().isOk())
				.andExpect(content().json(messageJson));
		doThrow(BookAlreadyAssignedToUserException.class).when(service).assignBookToUser("reshma98", 10);
		error.setError("The particular book is already assigned to user");
		String errorJson = mapper.writeValueAsString(error);
		mvc.perform(post("/library/users/reshma98/books/10")).andExpect(status().isBadRequest())
				.andExpect(content().json(errorJson));

	}
	@Test
	void removeTest() throws Exception {
		message.setMessage("Book is un-assigned to the user");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(service).removeAssignedBookFromUser("reshma98", 10);
		mvc.perform(delete("/library/users/reshma98/books/10")).andExpect(status().isOk())
				.andExpect(content().json(messageJson));
		doThrow(BookNotAssignedToUserException.class).when(service).removeAssignedBookFromUser("reshma98", 10);
		error.setError("The particular book is not assigned to user to release");
		String errorJson = mapper.writeValueAsString(error);
		mvc.perform(delete("/library/users/reshma98/books/10")).andExpect(status().isBadRequest())
				.andExpect(content().json(errorJson));

	}
}
