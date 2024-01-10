package com.epam.librarymanagement.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.service.ILibraryBookService;
import com.epam.librarymanagement.service.ILibraryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("library/books")
public class LibraryBookRestController {

	@Autowired
	ILibraryService libraryService;
	@Autowired
	ILibraryBookService bookService;

	@GetMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives the list of all books")
	public List<BookDto> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("{bookId}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives the book of that Id")
	public BookDto getBook(@PathVariable(name = "bookId") int bookId) {
		return bookService.getBook(bookId);
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("saves the book")
	public MessageDto addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@DeleteMapping("{bookId}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("Delete the book with particular Id")
	public MessageDto deleteBook(@PathVariable(name = "bookId") int bookId) {

		return bookService.deleteBook(bookId);
	}

	@PutMapping("{bookId}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("update the book with particular Id")
	public MessageDto updateBook(@RequestBody BookDto dto, @PathVariable(name = "bookId") int bookId) {
		return bookService.updateBook(dto, bookId);
	}
}
