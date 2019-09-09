package com.mfsi.appbuilder.controller;


import com.mfsi.appbuilder.master.document.User;
import com.mfsi.appbuilder.model.ApiResponse;
import com.mfsi.appbuilder.model.UserDto;
import com.mfsi.appbuilder.security.JwtTokenUtil;
import com.mfsi.appbuilder.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<String> generateToken(@RequestBody User loginUser) throws AuthenticationException {
    	
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        //final User user = userService.findOne(loginUser.getUsername());
        User user = new User();
        user.setUsername(authentication.getName());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<String>(200, "success", token);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ApiResponse<UserDto> signUp(@RequestBody User signUpUser) throws AuthenticationException {
        UserDto user = new UserDto();
        user.setUsername(signUpUser.getUsername());
        user.setPassword(signUpUser.getPassword());
        userService.save(user);
    
        return new ApiResponse<>(200, "success", user);
    }

}
