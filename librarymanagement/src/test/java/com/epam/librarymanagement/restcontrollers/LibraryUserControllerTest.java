package com.epam.librarymanagement.restcontrollers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;
import com.epam.librarymanagement.service.ILibraryUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class LibraryUserControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	ILibraryUserService userService;
	private static UserDto userDto = new UserDto();
	private ObjectMapper mapper = new ObjectMapper();
	private static MessageDto message = new MessageDto();

	@BeforeAll
	static void setup() {
		userDto.setUsername("reshmak");
		userDto.setEmail("reshmak@gmail.com");
		userDto.setName("reshma");
		message.setMessage("success");
	}

	@Test()
	void getAllUsersTest() throws Exception {
		List<UserDto> dtoList = new ArrayList<>();
		dtoList.add(userDto);
		String dtoListJson = mapper.writeValueAsString(dtoList);
		when(userService.getAllUsers()).thenReturn(dtoList);
		mvc.perform(get("/library/users")).andExpect(status().isOk()).andExpect(content().json(dtoListJson));
	}

	@Test
	void getUserTest() throws Exception {
		String userDtoJson = mapper.writeValueAsString(userDto);
		when(userService.getUser("reshma98")).thenReturn(userDto);
		mvc.perform(get("/library/users/reshma98")).andExpect(status().isOk()).andExpect(content().json(userDtoJson));
	}

	@Test
	void saveUserTest() throws Exception {
		String userDtoJson = mapper.writeValueAsString(userDto);
		String messageJson = mapper.writeValueAsString(message);
		when(userService.saveUser(ArgumentMatchers.any(UserDto.class))).thenReturn(message);
		mvc.perform(post("/library/users").contentType(MediaType.APPLICATION_JSON).content(userDtoJson)).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}
	@Test
	void updateUserTest() throws Exception{
		String userDtoJson = mapper.writeValueAsString(userDto);
		String messageJson = mapper.writeValueAsString(message);
		when(userService.updateUser(ArgumentMatchers.any(UserDto.class), ArgumentMatchers.anyString())).thenReturn(message);
		mvc.perform(put("/library/users/reshma98").contentType(MediaType.APPLICATION_JSON).content(userDtoJson)).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}
	@Test
	void deleteTest() throws Exception{
		when(userService.deleteUser("reshma98")).thenReturn(message);
		String messageJson = mapper.writeValueAsString(message);
		mvc.perform(delete("/library/users/reshma98")).andExpect(status().isOk()).andExpect(content().json(messageJson));
	}

}