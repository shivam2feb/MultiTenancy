package com.mfsi.appbuilder.service;


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

import com.mfsi.appbuilder.master.document.User;
import com.mfsi.appbuilder.master.repository.UserDao;
import com.mfsi.appbuilder.model.UserDto;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new com.mfsi.appbuilder.dto.AppUser(String.valueOf(user.getId()), user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
//		System.out.print(roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
//		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Iterable<User> userList = userDao.findAll();
        //userDao.findAll().iterator().forEachRemaining(list::add);
        userList.forEach(list::add);

        return list;
    }

    @Override
    public void delete(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if (user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
        return userDto;
    }

    @Override
    public User save(UserDto user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }
}
