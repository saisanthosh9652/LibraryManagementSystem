package com.epam.librarymanagement.restcontroller;

import java.util.List;

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

import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;
import com.epam.librarymanagement.service.ILibraryService;
import com.epam.librarymanagement.service.ILibraryUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("library/users")
public class LibraryUserRestController {

	@Autowired
	ILibraryService libraryService;
	@Autowired
	ILibraryUserService userService;

	@GetMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives the list of users")
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("gives user with particular username")
	public UserDto getUser(@PathVariable(name = "username") String username) {

		return userService.getUser(username);
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("saves the user")
	public MessageDto saveUser(@RequestBody UserDto dto) {
		return userService.saveUser(dto);
	}

	@DeleteMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("Deletes the user with particular username")
	public MessageDto deleteUser(@PathVariable(name = "username") String username) {

		return userService.deleteUser(username);
	}

	@PutMapping("{username}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation("updates the user with particular username")
	public MessageDto updateUser(@RequestBody UserDto details, @PathVariable(name = "username") String username) {

		return userService.updateUser(details, username);
	}
}
