package com.appsdeveloperblog.app.ws.service;

import java.sql.SQLException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto user) throws SQLException;

	UserDto getUser(String email);
}
