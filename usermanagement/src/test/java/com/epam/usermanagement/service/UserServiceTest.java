package com.epam.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.usermanagement.dto.UserDto;
import com.epam.usermanagement.entities.User;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;
import com.epam.usermanagement.repositories.UserRepository;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {
	@InjectMocks
	UserServiceImpl userService;
	@MockBean
	UserRepository userRepository;
	@MockBean
	ModelMapper mapper;
	@MockBean
	Type listType;
	@Test
	void getAllUsersTest() {
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setUsername("reshma98");
		user.setName("reshma");
		user.setEmail("reshma@gmail.com");
		userList.add(user);
		UserDto dto = new UserDto();
		dto.setUsername("reshma98");
		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(dto);
		when(userRepository.findAll()).thenReturn(userList);
		when(mapper.map(userList,listType)).thenReturn(userDtoList);
		assertEquals(userDtoList,userService.getAllUsers());
	}
	@Test
	void getUserTest() throws UserDoesNotExistsException {
		User user = new User();
		String username = "reshma98";
		when(userRepository.findById(username)).thenReturn(Optional.empty());
		assertThrows(UserDoesNotExistsException.class,()->userService.getUser(username));
		when(userRepository.findById(username)).thenReturn(Optional.ofNullable(user));
		UserDto dto = new UserDto();
		when(mapper.map(user, UserDto.class)).thenReturn(dto);
		assertEquals(dto,userService.getUser(username));
	}
	@Test
	void deleteTest() throws UserDoesNotExistsException {
		String username = "reshma98";
		when(userRepository.existsById(username)).thenReturn(false);
		assertThrows(UserDoesNotExistsException.class,()->userService.delete(username));
		doNothing().when(userRepository).deleteById(username);;
		when(userRepository.existsById(username)).thenReturn(true);
		userService.delete(username);
		verify(userRepository,times(2)).existsById(username);
		verify(userRepository,atLeastOnce()).deleteById(username);
	}
	@Test
	void saveTest() throws UserAlreadyExistsException {
		UserDto userDto = new UserDto();
		userDto.setName("reshma");
		userDto.setUsername("reshma98");
		userDto.setEmail("reshma@gmail.com");
		User user = new User();
		when(mapper.map(userDto, User.class)).thenReturn(user);
		when(userRepository.existsById("reshma98")).thenReturn(false);
		userService.save(userDto);
		verify(userRepository,atLeastOnce()).save(user);
		when(userRepository.existsById("reshma98")).thenReturn(true);
		assertThrows(UserAlreadyExistsException.class,()->userService.save(userDto));
	}
	@Test
	void updateTest() throws UserDoesNotExistsException {
		UserDto userdto = new UserDto();
		userdto.setUsername("reshma98");
		when(userRepository.findById("reshma98")).thenReturn(Optional.empty());
		assertThrows(UserDoesNotExistsException.class,()->userService.update(userdto, "reshma98"));
		User user = new User();
		when(userRepository.findById("reshma98")).thenReturn(Optional.ofNullable(user));
		userService.update(userdto, "reshma98");
		userdto.setUsername("reshma98");
		userdto.setName("reshma");;
		userdto.setEmail("reshmak@gmail.com");;
		userService.update(userdto, "reshma98");
		verify(userRepository,times(2)).save(user);
		
	}

}
