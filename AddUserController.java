package com.app.AddUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.entity.*;
import com.app.service.*;


@RestController 
public class AddUserController {
	
	@Autowired
	AddUserService userService;
	
	
	@RequestMapping(value="/addUser",method=RequestMethod.Put)
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
	

}
