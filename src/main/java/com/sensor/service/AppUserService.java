package com.sensor.service;

import java.util.List;

import com.sensor.dto.UserDTO;
import com.sensor.entity.AppUser;

public interface AppUserService {

	public UserDTO addUser(AppUser user);
	
	public AppUser findUserById(Long id);
	
	AppUser findByUserName(String username);
	
	List<AppUser> getAllUsers();
}
