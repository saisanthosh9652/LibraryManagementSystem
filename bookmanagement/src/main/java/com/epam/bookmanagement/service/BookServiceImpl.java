package com.epam.bookmanagement.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.bookmanagement.dto.BookDto;
import com.epam.bookmanagement.entities.Book;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;
import com.epam.bookmanagement.repositories.BookRepository;

@Service
public class BookServiceImpl implements IBookService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	ModelMapper mapper;
	@Autowired
	Type listType;

	@Override
	public List<BookDto> getAll() {
		return mapper.map(bookRepository.findAll(), listType);

	}

	@Override
	public BookDto getById(int id) throws NoBookFoundException {
		Optional<Book> bookOptional = bookRepository.findById(id);
		if (!bookOptional.isPresent())
			throw new NoBookFoundException();
		return mapper.map(bookOptional.get(), BookDto.class);
	}

	@Override
	public void add(BookDto dto) throws BookAlreadyPresentException {
		if (!bookRepository.findByName(dto.getName()).isPresent()) {
			Book book = mapper.map(dto, Book.class);
			bookRepository.save(book);
		} else {
			throw new BookAlreadyPresentException();
		}
	}

	@Override
	public void update(BookDto details, int id) throws NoBookFoundException {
		Optional<Book> bookOptional = bookRepository.findById(id);
		if (!bookOptional.isPresent())
			throw new NoBookFoundException();
		Book book = bookOptional.get();
		Optional<String> nameOptional = Optional.ofNullable(details.getName());
		Optional<String> authorOptional = Optional.ofNullable(details.getAuthor());
		Optional<String> publisherOptional = Optional.ofNullable(details.getPublisher());
		if (nameOptional.isPresent())
			book.setName(nameOptional.get());
		if (authorOptional.isPresent())
			book.setAuthor(authorOptional.get());
		if (publisherOptional.isPresent())
			book.setPublisher(publisherOptional.get());
		bookRepository.save(book);

	}

	@Override
	public void delete(int id) throws NoBookFoundException {
		if (!bookRepository.existsById(id))
			throw new NoBookFoundException();
		bookRepository.deleteById(id);

	}

	@Override
	public List<BookDto> getBooksByID(List<Integer> idList) {

		return mapper.map(bookRepository.findAllById(idList),listType);
	}

}
