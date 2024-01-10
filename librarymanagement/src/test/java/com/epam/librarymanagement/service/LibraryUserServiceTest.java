package com.epam.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.epam.librarymanagement.clients.BookProxy;
import com.epam.librarymanagement.clients.UserProxy;
import com.epam.librarymanagement.dto.BookDto;
import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class LibraryUserServiceTest {
	@InjectMocks
	LibraryUserServiceFeignClientImpl userService;
	@MockBean
	UserProxy userProxy;
	@MockBean
	BookProxy bookProxy;
	@MockBean
	ILibraryService libraryService;
	private MessageDto dto = new MessageDto();
	private UserDto userDto = new UserDto();
	private BookDto bookDto = new BookDto();
	@Test
	void getAllUsersTest() {
		List<UserDto> dtoList = new ArrayList<>();
		when(userProxy.getAllUsers()).thenReturn(dtoList);
		assertEquals(dtoList,userService.getAllUsers());
	}
	@Test
	void getUserTest() {
		UserDto dto = new UserDto();
		when(userProxy.getUser("reshma98")).thenReturn(dto);
		List<Integer> bookIdList = new ArrayList<>();
		bookIdList.add(1);
		when(libraryService.booksByUsername("reshma98")).thenReturn(bookIdList);
		when(bookProxy.getBook(1)).thenReturn(bookDto);
		assertEquals(dto, userService.getUser("reshma98"));
		when(userProxy.getUser("reshma98")).thenReturn(null);
		assertEquals(null, userService.getUser("reshma98"));
	}
	@Test
	void saveTest() {
		
		when(userProxy.save(userDto)).thenReturn(dto);
		assertEquals(dto,userService.saveUser(userDto));
	}
	@Test
	void updateTest() {
		when(userProxy.update(userDto, "reshma98")).thenReturn(dto);
		assertEquals(dto, userService.updateUser(userDto, "reshma98"));
	}
	@Test
	void deleteTest() {
		when(userProxy.delete("reshma98")).thenReturn(dto);
		doNothing().when(libraryService).deleteUserEntries("reshma98");
		assertEquals(dto,userService.deleteUser("reshma98"));
	}
}
