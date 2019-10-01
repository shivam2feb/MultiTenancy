package com.app.security.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.security.entity.User;
import com.app.security.repository.UserRepository;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {


	@Autowired
    private UserRepository userRepo;
	
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.print(bcryptEncoder.encode("Abcd@1234"));
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new com.app.security.dto.User(String.valueOf(user.getId()), user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
//		System.out.print(roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
//		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
