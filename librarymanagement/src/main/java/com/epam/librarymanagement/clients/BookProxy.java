package com.epam.librarymanagement.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;

@FeignClient(name = "book-management")
@LoadBalancerClient(name = "book-management")
public interface BookProxy {
	@GetMapping("books")
	List<BookDto> getAllBooks();

	@GetMapping("books/{id}")
	BookDto getBook(@PathVariable(name = "id") int id);

	@PostMapping("books")
	MessageDto add(@RequestBody @Valid BookDto dto);

	@DeleteMapping(value = "books/{id}")
	MessageDto delete(@PathVariable(name = "id") int id);

	@PutMapping(value = "books/{id}")
	MessageDto update(@RequestBody BookDto dto, @PathVariable(name = "id") int id);

	@GetMapping("books/by-id-list/{idList}")
	List<BookDto> getBooksListByIds(@PathVariable List<Integer> idList);
}
