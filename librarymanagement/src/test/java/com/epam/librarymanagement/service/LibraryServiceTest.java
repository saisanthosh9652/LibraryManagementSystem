package com.epam.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.clients.UserProxy;
import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.UserDto;
import com.epam.librarymanagement.entities.Library;
import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;
import com.epam.librarymanagement.repositories.LibraryRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {
	@InjectMocks
	LibraryServiceImpl libraryService;
	@MockBean
	LibraryRepository repository;
	@MockBean
	UserProxy userProxy;
	@MockBean
	BookProxy bookProxy;
	private Library library = new Library();
	@Test
	void assignBookToUserTest() throws BookAlreadyAssignedToUserException {
		when(userProxy.getUser("reshma98")).thenReturn(new UserDto());
		when(bookProxy.getBook(1)).thenReturn(new BookDto());
		when(repository.findByUsernameAndBookId("reshma98", 1)).thenReturn(Optional.ofNullable(library));
		assertThrows(BookAlreadyAssignedToUserException.class,()->libraryService.assignBookToUser("reshma98", 1));
		when(repository.findByUsernameAndBookId("reshma98", 1)).thenReturn(Optional.empty());
		libraryService.assignBookToUser("reshma98", 1);
		verify(repository,atLeastOnce()).save(ArgumentMatchers.any(Library.class));
	}
	@Test
	void removeAssignedBookFromUserTest() throws BookNotAssignedToUserException {
		when(userProxy.getUser("reshma98")).thenReturn(new UserDto());
		when(bookProxy.getBook(1)).thenReturn(new BookDto());
		when(repository.findByUsernameAndBookId("reshma98", 1)).thenReturn(Optional.empty());
		assertThrows(BookNotAssignedToUserException.class,()->libraryService.removeAssignedBookFromUser("reshma98", 1));
		library.setBookId(1);
		when(repository.findByUsernameAndBookId("reshma98", 1)).thenReturn(Optional.ofNullable(library));
		doNothing().when(repository).deleteById(1);
		libraryService.removeAssignedBookFromUser("reshma98", 1);
		verify(repository,atLeastOnce()).findByUsernameAndBookId("reshma98", 1);
	}
	@Test
	void deleteUserEntriesTest() {
		libraryService.deleteUserEntries("reshma98");
		verify(repository,atLeastOnce()).deleteByUsername("reshma98");
	}
	@Test
	void deleteBookEntriesTest() {
		libraryService.deleteBookEntries(1);
		verify(repository,atLeastOnce()).deleteByBookId(1);
	}
	@Test
	void booksByUsernameTest() {
		List<Library> libraryList = new ArrayList<>();
		library.setBookId(1);
		library.setUsername("reshma98");
		libraryList.add(library);
		when(repository.findByUsername("reshma98")).thenReturn(libraryList);
		libraryService.booksByUsername("reshma98");
		verify(repository,atLeastOnce()).findByUsername("reshma98");
	}

}
