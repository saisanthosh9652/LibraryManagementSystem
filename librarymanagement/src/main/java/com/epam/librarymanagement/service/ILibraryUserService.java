package com.epam.librarymanagement.service;

import java.util.List;

import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;

public interface ILibraryUserService {
	List<UserDto> getAllUsers();
	UserDto getUser(String username);
	MessageDto saveUser(UserDto dto);
	MessageDto updateUser(UserDto details,String username);
	MessageDto deleteUser(String username);
}
