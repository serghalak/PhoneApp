package com.appsdeveloperblog.app.ws.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Utils utils;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public UserDto createUser(UserDto user) throws SQLException {
		// TODO Auto-generated method stub
		
		if(userRepository.findUserByEmail(user.getEmail())!=null) {
			throw new SQLException("duplicate user");
		}
		
		
		UserEntity userEntity=new UserEntity();
		  	
		BeanUtils.copyProperties(user, userEntity);
		userEntity.setEncryptedPassword(
				bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));
		UserEntity userSaved=userRepository.save(userEntity);
		UserDto savedUserDto=new UserDto(); 
		BeanUtils.copyProperties(userSaved, savedUserDto);
		return savedUserDto;
	}


	@Override
	public UserDetails loadUserByUsername(String email) 
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userDb=userRepository.findUserByEmail(email);
		if(userDb == null) {
			throw new UsernameNotFoundException("authenticated is wrong");
		}
		
		return new User(
				userDb.getEmail()
				,userDb.getEncryptedPassword()
				,new ArrayList<>());
	}


	@Override
	public UserDto getUser(String email) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userRepository.findUserByEmail(email);
		if(userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		UserDto returnValue=new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

}
