package com.epam.librarymanagement.restcontrollers;

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

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.service.ILibraryBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class LibraryBookControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	ILibraryBookService bookService;
	private static BookDto bookDto = new BookDto();
	private ObjectMapper mapper = new ObjectMapper();
	private static MessageDto message = new MessageDto();

	@BeforeAll
	static void setup() {
		bookDto.setName("java");;
		bookDto.setAuthor("reshma");
		bookDto.setPublisher("oracle");
		message.setMessage("success");
	}

	@Test()
	void getAllbooksTest() throws Exception {
		List<BookDto> dtoList = new ArrayList<>();
		dtoList.add(bookDto);
		String dtoListJson = mapper.writeValueAsString(dtoList);
		when(bookService.getAllBooks()).thenReturn(dtoList);
		mvc.perform(get("/library/books")).andExpect(status().isOk()).andExpect(content().json(dtoListJson));
	}

	@Test
	void getbookTest() throws Exception {
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		when(bookService.getBook(10)).thenReturn(bookDto);
		mvc.perform(get("/library/books/10")).andExpect(status().isOk()).andExpect(content().json(bookDtoJson));
	}

	@Test
	void savebookTest() throws Exception {
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		String messageJson = mapper.writeValueAsString(message);
		when(bookService.addBook(ArgumentMatchers.any(BookDto.class))).thenReturn(message);
		mvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(bookDtoJson)).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}
	@Test
	void updatebookTest() throws Exception{
		String bookDtoJson = mapper.writeValueAsString(bookDto);
		String messageJson = mapper.writeValueAsString(message);
		when(bookService.updateBook(ArgumentMatchers.any(BookDto.class), ArgumentMatchers.anyInt())).thenReturn(message);
		mvc.perform(put("/library/books/10").contentType(MediaType.APPLICATION_JSON).content(bookDtoJson)).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}
	@Test
	void deleteTest() throws Exception{
		when(bookService.deleteBook(10)).thenReturn(message);
		String messageJson = mapper.writeValueAsString(message);
		mvc.perform(delete("/library/books/10")).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}

}