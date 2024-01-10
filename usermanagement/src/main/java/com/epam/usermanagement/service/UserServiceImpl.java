package com.epam.usermanagement.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.usermanagement.dto.UserDto;
import com.epam.usermanagement.entities.User;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;
import com.epam.usermanagement.repositories.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper mapper;
	@Autowired
	Type listType;

	@Override
	public void save(UserDto dto) throws UserAlreadyExistsException {
		if (userRepository.existsById(dto.getUsername()))
			throw new UserAlreadyExistsException();
		User user = mapper.map(dto, User.class);
		userRepository.save(user);
	}

	@Override
	public void update(UserDto dto, String username) throws UserDoesNotExistsException {
		Optional<User> userOptional = userRepository.findById(username);
		if (!userOptional.isPresent())
			throw new UserDoesNotExistsException();
		User user = userOptional.get();
		Optional<String> nameOptionl = Optional.ofNullable(dto.getName());
		Optional<String> emailOptional = Optional.ofNullable(dto.getEmail());
		if (nameOptionl.isPresent())
			user.setName(nameOptionl.get());
		if (emailOptional.isPresent())
			user.setEmail(emailOptional.get());
		userRepository.save(user);
	}

	@Override
	public void delete(String username) throws UserDoesNotExistsException {
		if (!userRepository.existsById(username))
			throw new UserDoesNotExistsException();
		userRepository.deleteById(username);
	}

	@Override
	public UserDto getUser(String username) throws UserDoesNotExistsException {
		Optional<User> userOptional = userRepository.findById(username);
		if (!userOptional.isPresent())
			throw new UserDoesNotExistsException();
		return mapper.map(userOptional.get(), UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		return mapper.map(userRepository.findAll(), listType);
	}

}
