package com.epam.librarymanagement.service;

import java.util.List;

import com.epam.librarymanagement.exceptions.BookAlreadyAssignedToUserException;
import com.epam.librarymanagement.exceptions.BookNotAssignedToUserException;

public interface ILibraryService {
	void assignBookToUser(String username,int bookID) throws BookAlreadyAssignedToUserException;
	void removeAssignedBookFromUser(String username,int bookId) throws BookNotAssignedToUserException;
	void deleteUserEntries(String username);
	void deleteBookEntries(int bookId);
	List<Integer> booksByUsername(String username);
}
