package com.epam.librarymanagement.service;

import java.util.List;

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;

public interface ILibraryBookService {
	List<BookDto> getAllBooks();
	BookDto getBook(int id);
	MessageDto addBook(BookDto dto);
	MessageDto updateBook(BookDto details,int id);
	MessageDto deleteBook(int id);
}
