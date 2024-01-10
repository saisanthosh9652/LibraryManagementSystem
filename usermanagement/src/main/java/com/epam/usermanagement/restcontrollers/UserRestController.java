package com.epam.usermanagement.restcontrollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.usermanagement.dto.MessageDto;
import com.epam.usermanagement.dto.UserDto;
import com.epam.usermanagement.exceptions.UserAlreadyExistsException;
import com.epam.usermanagement.exceptions.UserDoesNotExistsException;
import com.epam.usermanagement.service.IUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("users")
public class UserRestController {
	@Autowired
	IUserService userService;
	private MessageDto message = new MessageDto();
	private static final String ADD_SUCCESS = "User added successfully";
	private static final String DELETE_SUCCESS = "User deleted successfully";
	private static final String UPDATE_SUCCESS = "User updated successfully";
	
	@GetMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives the list of users")
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives user with particular username")
	public UserDto getUser(@PathVariable(name = "username") String username)
			throws UserDoesNotExistsException {
		return userService.getUser(username);
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("saves the user")
	public MessageDto save(@RequestBody @Valid UserDto dto) throws UserAlreadyExistsException {
		userService.save(dto);
		message.setMessage(ADD_SUCCESS);
		return message;
	}

	@PutMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("updates the user with particular username")
	public MessageDto update(@RequestBody UserDto details,
			@PathVariable(name = "username") String username) throws UserDoesNotExistsException {
		userService.update(details, username);
		message.setMessage(UPDATE_SUCCESS);
		return message;

	}

	@DeleteMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("Deletes the user with particular username")
	public MessageDto delete(@PathVariable(name = "username") String username)
			throws UserDoesNotExistsException {
		userService.delete(username);
		message.setMessage(DELETE_SUCCESS);
		return message;

	}

}
