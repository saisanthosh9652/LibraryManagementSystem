package com.epam.bookmanagement.restcontroller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.bookmanagement.BookmanagementApplication;
import com.epam.bookmanagement.dto.BookDto;
import com.epam.bookmanagement.dto.ErrorDto;
import com.epam.bookmanagement.dto.MessageDto;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;
import com.epam.bookmanagement.service.IBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { BookmanagementApplication.class })
class BookRestControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	IBookService bookService;
	private ObjectMapper mapper = new ObjectMapper();
	private static BookDto bookdto = new BookDto();
	private MessageDto message = new MessageDto();
	private ErrorDto error = new ErrorDto();

	@BeforeAll
	static void setUp() {
		bookdto.setName("java");
		bookdto.setAuthor("tek Systems");
		bookdto.setPublisher("oracle");
	}

	@Test()
	void getAllBooksTest() throws Exception {
		List<BookDto> dtoList = new ArrayList<>();
		dtoList.add(bookdto);
		String dtoListJson = mapper.writeValueAsString(dtoList);
		when(bookService.getAll()).thenReturn(dtoList);
		mvc.perform(get("/books")).andExpect(status().isOk()).andExpect(content().json(dtoListJson));
	}

	@Test()
	void getBookTestSuccess() throws Exception {
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		when(bookService.getById(10)).thenReturn(bookdto);
		mvc.perform(get("/books/10")).andExpect(status().isOk()).andExpect(content().json(bookdtoJson));

	}

	@Test
	void getBookThrowsExceptionTest() throws Exception {
		error.setError("There is no book with the particular ID");
		String errorJson = mapper.writeValueAsString(error);
		when(bookService.getById(10)).thenThrow(NoBookFoundException.class);
		mvc.perform(get("/books/10")).andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test()
	void addTest() throws Exception {
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		message.setMessage("Book added successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(bookService).add(ArgumentMatchers.any(BookDto.class));
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookdtoJson))
				.andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void addThrowsExceptionTest() throws Exception {
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		error.setError("The book is already present");
		String errorJson = mapper.writeValueAsString(error);
		doThrow(BookAlreadyPresentException.class).when(bookService).add(ArgumentMatchers.any(BookDto.class));
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test
	void addInvalidInputTest() throws Exception {
		bookdto.setAuthor("me");
		List<String> errors = new ArrayList<>();
		errors.add("author name is Not proper");
		error.setError(errors.toString());
		String errorJson = mapper.writeValueAsString(error);
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test
	void updateTest() throws Exception {
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		message.setMessage("Book updated successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(bookService).update(ArgumentMatchers.any(BookDto.class), ArgumentMatchers.any(Integer.class));
		mvc.perform(put("/books/10").contentType(MediaType.APPLICATION_JSON).content(bookdtoJson))
				.andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void updateThrowsExceptionTest() throws Exception {
		String bookdtoJson = mapper.writeValueAsString(bookdto);
		error.setError("There is no book with the particular ID");
		String errorJson = mapper.writeValueAsString(error);
		doThrow(NoBookFoundException.class).when(bookService).update(ArgumentMatchers.any(BookDto.class),
				ArgumentMatchers.any(Integer.class));
		mvc.perform(put("/books/10").contentType(MediaType.APPLICATION_JSON).content(bookdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));

	}

	@Test
	void deleteTest() throws Exception {
		message.setMessage("Book deleted successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(bookService).delete(10);
		mvc.perform(delete("/books/10")).andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void deletTestThrowsException() throws Exception {
		doThrow(NoBookFoundException.class).when(bookService).delete(10);
		error.setError("There is no book with the particular ID");
		String errorJson = mapper.writeValueAsString(error);
		mvc.perform(delete("/books/10")).andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}
	
	@Test()
	void getAllBooksByIDTest() throws Exception {
		List<BookDto> dtoList = new ArrayList<>();
		dtoList.add(bookdto);
		String dtoListJson = mapper.writeValueAsString(dtoList);
		when(bookService.getBooksByID(ArgumentMatchers.anyList())).thenReturn(dtoList);
		mvc.perform(get("/books/by-id-list/1,2")).andExpect(status().isOk()).andExpect(content().json(dtoListJson));
	}

}
