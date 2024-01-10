package com.epam.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.clients.UserProxy;
import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LibraryBookServiceTest {
	@InjectMocks
	LibraryBookServiceFeignClientImpl bookService;
	@MockBean
	UserProxy userProxy;
	@MockBean
	BookProxy bookProxy;
	@MockBean
	ILibraryService libraryService;
	private MessageDto message = new MessageDto();
	private BookDto bookDto = new BookDto();

	@Test()
	void getAllBookTest() {
		List<BookDto> bookDtoList = new ArrayList<>();
		when(bookProxy.getAllBooks()).thenReturn(bookDtoList);
		assertEquals(bookDtoList, bookService.getAllBooks());
	}

	@Test
	void getBookTest() {
		when(bookProxy.getBook(1)).thenReturn(bookDto);
		assertEquals(bookDto, bookService.getBook(1));
	}

	@Test
	void addTest() {
		when(bookProxy.add(bookDto)).thenReturn(message);
		assertEquals(message, bookService.addBook(bookDto));
	}

	@Test
	void updateTest() {
		when(bookProxy.update(bookDto, 1)).thenReturn(message);
		assertEquals(message, bookService.updateBook(bookDto, 1));
	}

	@Test
	void deleteTest() {
		when(bookProxy.delete(1)).thenReturn(message);
		doNothing().when(libraryService).deleteBookEntries(1);
		assertEquals(message, bookService.deleteBook(1));
	}

}
