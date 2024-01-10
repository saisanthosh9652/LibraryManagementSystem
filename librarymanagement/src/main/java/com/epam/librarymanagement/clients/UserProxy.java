package com.epam.librarymanagement.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.librarymanagement.dto.MessageDto;
import com.epam.librarymanagement.dto.UserDto;

@FeignClient(name = "user-management")
@LoadBalancerClient(name="user-management")
public interface UserProxy {
	@GetMapping("users")
	List<UserDto> getAllUsers();

	@GetMapping("users/{username}")
	UserDto getUser(@PathVariable(name = "username") String username);

	@PostMapping("users")
	MessageDto save(@RequestBody @Valid UserDto dto);

	@PutMapping("users/{username}")
	MessageDto update(@RequestBody UserDto details, @PathVariable(name = "username") String username);

	@DeleteMapping("users/{username}")
	MessageDto delete(@PathVariable(name = "username") String username);
}
