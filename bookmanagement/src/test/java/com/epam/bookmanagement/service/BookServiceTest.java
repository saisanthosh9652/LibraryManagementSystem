package com.epam.bookmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.bookmanagement.dto.BookDto;
import com.epam.bookmanagement.entities.Book;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;
import com.epam.bookmanagement.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookServiceTest {
	@InjectMocks
	BookServiceImpl bookService;
	@MockBean
	BookRepository bookRepository;
	@MockBean
	ModelMapper mapper;
	@MockBean
	Type type;
	@Test
	void getAllTest() {
		List<Book> bookList = new ArrayList<>();
		Book book = new Book();
		book.setName("java");
		bookList.add(book);
		BookDto dto = new BookDto();
		when(bookRepository.findAll()).thenReturn(bookList);
		when(mapper.map(bookList,type)).thenReturn(Arrays.asList(dto));
		assertEquals(dto,bookService.getAll().get(0));
	}

	@Test
	void getByIdTest() throws NoBookFoundException {
		Book book = new Book();
		int bookId = 10;
		when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
		assertThrows(NoBookFoundException.class, () -> bookService.getById(bookId));
		when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
		BookDto dto = new BookDto();
		when(mapper.map(book, BookDto.class)).thenReturn(dto);
		assertEquals(dto, bookService.getById(bookId));
	}

	@Test
	void deleteTest() throws NoBookFoundException {
		int bookId = 10;
		when(bookRepository.existsById(10)).thenReturn(false);
		assertThrows(NoBookFoundException.class, () -> bookService.delete(bookId));
		doNothing().when(bookRepository).deleteById(10);
		when(bookRepository.existsById(10)).thenReturn(true);
		bookService.delete(bookId);
		verify(bookRepository, times(2)).existsById(10);
		verify(bookRepository, atLeastOnce()).deleteById(10);
	}

	@Test
	void addTest() throws BookAlreadyPresentException {
		BookDto bookDto = new BookDto();
		bookDto.setName("java");
		Book book = new Book();
		when(mapper.map(bookDto, Book.class)).thenReturn(book);
		when(bookRepository.findByName("java")).thenReturn(Optional.empty());
		bookService.add(bookDto);
		verify(bookRepository, atLeastOnce()).save(book);
		when(bookRepository.findByName("java")).thenReturn(Optional.ofNullable(book));
		assertThrows(BookAlreadyPresentException.class, () -> bookService.add(bookDto));
	}

	@Test
	void updateTest() throws NoBookFoundException {
		BookDto bookdto = new BookDto();
		when(bookRepository.findById(10)).thenReturn(Optional.empty());
		assertThrows(NoBookFoundException.class, () -> bookService.update(bookdto, 10));
		Book book = new Book();
		when(bookRepository.findById(10)).thenReturn(Optional.ofNullable(book));
		bookService.update(bookdto, 10);
		bookdto.setName("java");
		bookdto.setAuthor("tek Systems");
		bookdto.setPublisher("oracle");
		bookService.update(bookdto, 10);
		verify(bookRepository, times(2)).save(book);

	}
	@Test
	void getBooksByIdTest() {
		List<Book> bookList = new ArrayList<>();
		Book book = new Book();
		book.setName("java");
		bookList.add(book);
		BookDto dto = new BookDto();
		when(bookRepository.findAllById(Arrays.asList(1))).thenReturn(bookList);
		when(mapper.map(bookList,type)).thenReturn(Arrays.asList(dto));
		assertEquals(dto,bookService.getBooksByID(Arrays.asList(1)).get(0));
	}

}
