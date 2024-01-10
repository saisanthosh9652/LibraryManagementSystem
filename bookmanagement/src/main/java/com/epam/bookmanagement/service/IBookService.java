package com.epam.bookmanagement.service;

import java.util.List;

import com.epam.bookmanagement.dto.BookDto;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;

public interface IBookService {
	List<BookDto> getAll();
	BookDto getById(int id) throws NoBookFoundException;
	void add(BookDto dto) throws BookAlreadyPresentException;
	void update(BookDto details,int id) throws NoBookFoundException;
	void delete(int id) throws NoBookFoundException;
	List<BookDto> getBooksByID(List<Integer> idList);
}
