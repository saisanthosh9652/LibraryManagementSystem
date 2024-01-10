package com.epam.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.clients.UserProxy;
import com.epam.librarymanagement.entities.Library;
import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;
import com.epam.librarymanagement.repositories.LibraryRepository;

@Service
public class LibraryServiceImpl implements ILibraryService {
	@Autowired
	LibraryRepository libraryrepository;
	@Autowired
	UserProxy userProxy;
	@Autowired
	BookProxy bookProxy;

	@Override
	public void assignBookToUser(String username, int bookId) throws BookAlreadyAssignedToUserException {
		userProxy.getUser(username);
		bookProxy.getBook(bookId);
		Optional<Library> libraryOptional = libraryrepository.findByUsernameAndBookId(username, bookId);
		if (libraryOptional.isPresent())
			throw new BookAlreadyAssignedToUserException();
		Library libraryEntry = new Library();
		libraryEntry.setUsername(username);
		libraryEntry.setBookId(bookId);
		libraryrepository.save(libraryEntry);

	}

	@Override
	public void removeAssignedBookFromUser(String username, int bookId) throws BookNotAssignedToUserException {
		userProxy.getUser(username);
		bookProxy.getBook(bookId);
		Optional<Library> libraryOptional = libraryrepository.findByUsernameAndBookId(username, bookId);
		if (!libraryOptional.isPresent())
			throw new BookNotAssignedToUserException();
		libraryrepository.deleteById(libraryOptional.get().getId());
	}

	@Override
	public void deleteUserEntries(String username) {
		libraryrepository.deleteByUsername(username);

	}

	@Override
	public void deleteBookEntries(int bookId) {
		libraryrepository.deleteByBookId(bookId);

	}

	@Override
	public List<Integer> booksByUsername(String username) {
		List<Integer> bookIdList = new ArrayList<>();
		libraryrepository.findByUsername(username).forEach(library -> bookIdList.add(library.getBookId()));
		return bookIdList;
	}

}
