package com.app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);
}
