package com.epam.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
@Service
public class LibraryBookServiceRestTemplateImpl implements ILibraryBookService {
	private RestTemplate restTemplate = new RestTemplate();
	private String root = "http://localhost:8081/books/";
	@Autowired
	ILibraryService libraryService;
	@SuppressWarnings("unchecked")
	@Override
	public List<BookDto> getAllBooks() {
		return restTemplate.getForObject(root, List.class);
	}

	@Override
	public BookDto getBook(int id) {
		return restTemplate.getForObject(root + id, BookDto.class);
	}

	@Override
	public MessageDto addBook(BookDto dto) {
		return restTemplate.postForObject(root, dto, MessageDto.class);
	}

	@Override
	public MessageDto updateBook(BookDto details, int id) {
		HttpEntity<BookDto> entity = new HttpEntity<>(details);
		ResponseEntity<MessageDto> message = restTemplate.exchange(root + id, HttpMethod.PUT, entity, MessageDto.class);
		return message.getBody();
	}

	@Override
	public MessageDto deleteBook(int id) {
		ResponseEntity<MessageDto> message = restTemplate.exchange(root + id, HttpMethod.DELETE, null,
				MessageDto.class);
		libraryService.deleteBookEntries(id);
		return message.getBody();
	}

}
