package com.epam.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;
@Service
public class LibraryUserServiceRestTemplateImpl implements ILibraryUserService{
	private RestTemplate restTemplate = new RestTemplate();
	private String root = "http://localhost:8082/users/";
	@Autowired
	ILibraryService libraryService;
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDto> getAllUsers() {
		return restTemplate.getForObject(root, List.class);
	}

	@Override
	public UserDto getUser(String username) {
		UserDto userDto = restTemplate.getForObject("http://localhost:8082/users/" + username, UserDto.class);
		List<BookDto> bookDtoList = new ArrayList<>();
		libraryService.booksByUsername(username).forEach(bookId -> {
			BookDto bookDto = restTemplate.getForObject("http://localhost:8081/books/" + bookId, BookDto.class);
			bookDtoList.add(bookDto);
		});
		Optional<UserDto> dtoOptional = Optional.ofNullable(userDto);
		if(dtoOptional.isPresent())
			userDto.setBooksList(bookDtoList);
		return userDto;
	}

	@Override
	public MessageDto saveUser(UserDto dto) {
		return restTemplate.postForObject(root, dto, MessageDto.class);
		
	}

	@Override
	public MessageDto updateUser(UserDto details, String username) {
		HttpEntity<UserDto> entity = new HttpEntity<>(details);
		ResponseEntity<MessageDto> message = restTemplate.exchange(root + username, HttpMethod.PUT, entity, MessageDto.class);
		return message.getBody();
	}

	@Override
	public MessageDto deleteUser(String username) {
		ResponseEntity<MessageDto> message = restTemplate.exchange(root + username, HttpMethod.DELETE, null,
				MessageDto.class);
		libraryService.deleteUserEntries(username);
		return message.getBody();
	}

}
