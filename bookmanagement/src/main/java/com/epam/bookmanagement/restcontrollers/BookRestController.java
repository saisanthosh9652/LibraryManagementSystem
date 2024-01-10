package com.epam.bookmanagement.restcontrollers;

import java.util.List;

import javax.validation.Valid;

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

import com.epam.bookmanagement.dto.BookDto;
import com.epam.bookmanagement.dto.MessageDto;
import com.epam.bookmanagement.exceptions.BookAlreadyPresentException;
import com.epam.bookmanagement.exceptions.NoBookFoundException;
import com.epam.bookmanagement.service.IBookService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("books")
public class BookRestController {
	@Autowired
	IBookService bookService;
	private MessageDto message = new MessageDto();
	private static final String ADD_SUCCESS = "Book added successfully";
	private static final String DELETE_SUCCESS = "Book deleted successfully";
	private static final String UPDATE_SUCCESS = "Book updated successfully";

	@GetMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives all the books")
	public List<BookDto> getAllBooks() {
		return bookService.getAll();
	}

	@GetMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives the book of that Id")
	public BookDto getBook(@PathVariable(name = "id") int id) throws NoBookFoundException {
		return bookService.getById(id);
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("saves the book")
	public MessageDto add(@RequestBody @Valid BookDto dto) throws BookAlreadyPresentException {
		bookService.add(dto);
		message.setMessage(ADD_SUCCESS);
		return message;
	}

	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("Delete the book with particular Id")
	public MessageDto delete(@PathVariable(name = "id") int id) throws NoBookFoundException {
		bookService.delete(id);
		message.setMessage(DELETE_SUCCESS);
		return message;
	}

	@PutMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("update the book with particular Id")
	public MessageDto update(@RequestBody BookDto dto, @PathVariable(name = "id") int id)
			throws NoBookFoundException {
		bookService.update(dto, id);
		message.setMessage(UPDATE_SUCCESS);
		return message;

	}
	@GetMapping("by-id-list/{idList}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives books with ids in the given list")
	public List<BookDto> getBooksListByIds(@PathVariable(name="idList") List<Integer> idList){
		return bookService.getBooksByID(idList);
	}

}
