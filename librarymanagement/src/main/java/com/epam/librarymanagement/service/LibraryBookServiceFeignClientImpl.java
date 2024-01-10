package com.epam.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;

@Service
@Primary
public class LibraryBookServiceFeignClientImpl implements ILibraryBookService {
	@Autowired
	BookProxy proxy;
	@Autowired
	ILibraryService libraryService;

	@Override
	public List<BookDto> getAllBooks() {
		return proxy.getAllBooks();
	}

	@Override
	public BookDto getBook(int id) {
		return proxy.getBook(id);
	}

	@Override
	public MessageDto addBook(BookDto dto) {
		return proxy.add(dto);
	}

	@Override
	public MessageDto updateBook(BookDto details, int id) {
		return proxy.update(details, id);
	}

	@Override
	public MessageDto deleteBook(int id) {
		MessageDto message = proxy.delete(id);
		libraryService.deleteBookEntries(id);
		return message;
	}

}
