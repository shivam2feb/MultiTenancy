package com.mfsi.appbuilder.service;


import com.mfsi.appbuilder.master.document.User;
import com.mfsi.appbuilder.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    List<User> findAll();

    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
