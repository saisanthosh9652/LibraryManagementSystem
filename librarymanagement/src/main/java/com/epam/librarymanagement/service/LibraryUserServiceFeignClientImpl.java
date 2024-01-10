package com.epam.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.clients.UserProxy;
import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;

@Service
@Primary
public class LibraryUserServiceFeignClientImpl implements ILibraryUserService {
	@Autowired
	UserProxy userProxy;
	@Autowired
	BookProxy bookProxy;
	@Autowired
	ILibraryService libraryService;

	@Override
	public List<UserDto> getAllUsers() {
		return userProxy.getAllUsers();
	}

	@Override
	public UserDto getUser(String username) {
		UserDto userDto = userProxy.getUser(username);
		List<Integer> idList = libraryService.booksByUsername(username);
		List<BookDto> bookDtoList = bookProxy.getBooksListByIds(idList);
		userDto.setBooksList(bookDtoList);
		return userDto;
	}

	@Override
	public MessageDto saveUser(UserDto dto) {
		return userProxy.save(dto);
	}

	@Override
	public MessageDto updateUser(UserDto details, String username) {
		return userProxy.update(details, username);
	}

	@Override
	public MessageDto deleteUser(String username) {
		MessageDto message = userProxy.delete(username);
		libraryService.deleteUserEntries(username);
		return message;
	}

}
