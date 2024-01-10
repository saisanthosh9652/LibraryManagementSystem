package com.epam.usermanagement.restcontroller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import com.epam.usermanagement.UsermanagementApplication;
import com.epam.usermanagement.dto.ErrorDto;
import com.epam.usermanagement.dto.MessageDto;
import com.epam.usermanagement.dto.UserDto;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;
import com.epam.usermanagement.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { UsermanagementApplication.class })
class UserRestControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	IUserService userService;
	private ObjectMapper mapper = new ObjectMapper();
	private static UserDto userdto = new UserDto();
	private MessageDto message = new MessageDto();
	private ErrorDto error = new ErrorDto();

	@BeforeAll
	static void setUp() {
		userdto.setName("reshma");
		userdto.setUsername("reshma98");
		userdto.setEmail("reshmak@gmail.com");
	}

	@Test()
	void getAllUsersTest() throws Exception {
		List<UserDto> dtoList = new ArrayList<>();
		dtoList.add(userdto);
		String dtoListJson = mapper.writeValueAsString(dtoList);
		when(userService.getAllUsers()).thenReturn(dtoList);
		mvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json(dtoListJson));
	}

	@Test()
	void getUserTestSuccess() throws Exception {
		String userdtoJson = mapper.writeValueAsString(userdto);
		when(userService.getUser("reshma98")).thenReturn(userdto);
		mvc.perform(get("/users/reshma98")).andExpect(status().isOk()).andExpect(content().json(userdtoJson));

	}

	@Test
	void getUserThrowsExceptionTest() throws Exception {
		error.setError("There is no user with the particular username");
		String errorJson = mapper.writeValueAsString(error);
		when(userService.getUser("reshma98")).thenThrow(UserDoesNotExistsException.class);
		mvc.perform(get("/users/reshma98")).andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test()
	void addTest() throws Exception {
		String userdtoJson = mapper.writeValueAsString(userdto);
		message.setMessage("User added successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(userService).save(ArgumentMatchers.any(UserDto.class));
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userdtoJson))
				.andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void addThrowsExceptionTest() throws Exception {
		String userdtoJson = mapper.writeValueAsString(userdto);
		error.setError("The user is already present");
		String errorJson = mapper.writeValueAsString(error);
		doThrow(UserAlreadyExistsException.class).when(userService).save(ArgumentMatchers.any(UserDto.class));
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test
	void addInvalidInputTest() throws Exception {
		userdto.setName("me");
		List<String> errors = new ArrayList<>();
		errors.add("name is not proper");
		error.setError(errors.toString());
		String errorJson = mapper.writeValueAsString(error);
		String userdtoJson = mapper.writeValueAsString(userdto);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

	@Test
	void updateTest() throws Exception {
		String userdtoJson = mapper.writeValueAsString(userdto);
		message.setMessage("User updated successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(userService).update(ArgumentMatchers.any(UserDto.class), ArgumentMatchers.any(String.class));
		mvc.perform(put("/users/reshma98").contentType(MediaType.APPLICATION_JSON).content(userdtoJson))
				.andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void updateThrowsExceptionTest() throws Exception {
		String userdtoJson = mapper.writeValueAsString(userdto);
		error.setError("There is no user with the particular username");
		String errorJson = mapper.writeValueAsString(error);
		doThrow(UserDoesNotExistsException.class).when(userService).update(ArgumentMatchers.any(UserDto.class),
				ArgumentMatchers.any(String.class));
		mvc.perform(put("/users/reshma98").contentType(MediaType.APPLICATION_JSON).content(userdtoJson))
				.andExpect(status().isBadRequest()).andExpect(content().json(errorJson));

	}

	@Test
	void deleteTest() throws Exception {
		message.setMessage("User deleted successfully");
		String messageJson = mapper.writeValueAsString(message);
		doNothing().when(userService).delete("reshma98");
		mvc.perform(delete("/users/reshma98")).andExpect(status().isOk()).andExpect(content().json(messageJson));

	}

	@Test
	void deletTestThrowsException() throws Exception {
		doThrow(UserDoesNotExistsException.class).when(userService).delete("reshma98");
		error.setError("There is no user with the particular username");
		String errorJson = mapper.writeValueAsString(error);
		mvc.perform(delete("/users/reshma98")).andExpect(status().isBadRequest()).andExpect(content().json(errorJson));
	}

}
