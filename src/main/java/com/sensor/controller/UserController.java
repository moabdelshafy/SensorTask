package com.sensor.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sensor.dto.UserDTO;
import com.sensor.entity.AppUser;
import com.sensor.service.AppUserService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/addUser")
	public UserDTO addUser(@RequestBody @Valid AppUser appUser) {
		AppUser user = appUserService.addUser(appUser);
		return modelMapper.map(user, UserDTO.class);
	}

	@GetMapping("/getUserById/{id}")
	public UserDTO getUserById(@PathVariable Long id) {
		AppUser user = appUserService.findUserById(id);
		return modelMapper.map(user, UserDTO.class);
	}

	@GetMapping("/findByUserName/{username}")
	public UserDTO findByUserName(@PathVariable String username) {
		AppUser user = appUserService.findByUserName(username);
		return modelMapper.map(user, UserDTO.class);
	}

	@GetMapping("/allUsers")
	public List<UserDTO> getAllUsers() {
		List<AppUser> users = appUserService.getAllUsers();
		return users.stream().map(entity -> modelMapper.map(entity, UserDTO.class)).collect(Collectors.toList());
	}

}
