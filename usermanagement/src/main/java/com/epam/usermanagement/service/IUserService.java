package com.epam.usermanagement.service;

import java.util.List;

import com.epam.usermanagement.dto.UserDto;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;

public interface IUserService {
	void save(UserDto dto) throws UserAlreadyExistsException;
	void update(UserDto dto,String username) throws UserDoesNotExistsException;
	void delete(String username) throws UserDoesNotExistsException;
	UserDto getUser(String username) throws UserDoesNotExistsException;
	List<UserDto> getAllUsers();
}
