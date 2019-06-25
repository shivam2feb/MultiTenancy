package com.app.AddUser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.app.entity.*;
import com.app.repository.*;


@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class AddUserServiceImpl implements AddUserService{
	
	@Autowired 
	AddUserRepository repository;
	
	public void updateUser(User user) {
		repository.save(user);
		
	}
}
