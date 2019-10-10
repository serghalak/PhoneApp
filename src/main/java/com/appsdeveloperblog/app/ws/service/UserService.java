package com.appsdeveloperblog.app.ws.service;

import java.sql.SQLException;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user) throws SQLException;

}
