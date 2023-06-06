package com.sensor.controller;

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

	@PostMapping("/addUser")
	public UserDTO addUser(@RequestBody @Valid AppUser appUser) {
		return appUserService.addUser(appUser);
	}

	@GetMapping("/getUserById/{id}")
	public AppUser getUserById(@PathVariable Long id) {
		return appUserService.findUserById(id);
	}

	@GetMapping("/findByUserName/{username}")
	public AppUser findByUserName(@PathVariable String username) {
		return appUserService.findByUserName(username);
	}

}
