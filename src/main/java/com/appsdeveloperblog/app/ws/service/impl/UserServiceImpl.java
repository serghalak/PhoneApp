package com.appsdeveloperblog.app.ws.service.impl;

import java.sql.SQLException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.UserRepository;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Utils utils;
	
	@Override
	public UserDto createUser(UserDto user) throws SQLException {
		// TODO Auto-generated method stub
		
		if(userRepository.findUserByEmail(user.getEmail())!=null) {
			throw new SQLException("duplicate user");
		}
		
		
		UserEntity userEntity=new UserEntity();
		
		BeanUtils.copyProperties(user, userEntity);
		userEntity.setEncryptedPassword("cript");
		userEntity.setUserId(utils.generateUserId(30));
		UserEntity userSaved=userRepository.save(userEntity);
		UserDto savedUserDto=new UserDto(); 
		BeanUtils.copyProperties(userSaved, savedUserDto);
		return savedUserDto;
	}

}
