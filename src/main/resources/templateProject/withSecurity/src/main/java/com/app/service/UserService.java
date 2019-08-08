package com.app.service;

import java.util.List;

import com.app.dto.UserDto;
import com.app.entity.User;

public interface UserService {

	User save(UserDto user);

	List<User> findAll();

	User findOne(String username);

}
