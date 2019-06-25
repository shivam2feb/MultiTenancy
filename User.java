package com.app.AddUser.entity;

import java.util.*;
import javax.persistence.*;
import com.app.entity.*;

@Entity(name="User")
public class User {
	
	@Column(name="id")
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Column(name="user_name")
	private String user_name;
	
	public String getUser_name(){
		return user_name;
	}
	
	public void setUser_name(String user_name){
		this.user_name=user_name;
	}
	
	@Column(name="user_salary")
	private Long user_salary;
	
	public Long getUser_salary(){
		return user_salary;
	}
	
	public void setUser_salary(Long user_salary){
		this.user_salary=user_salary;
	}
	
	@Column(name="security")
	private Security security;
	
	public Security getSecurity(){
		return security;
	}
	
	public void setSecurity(Security security){
		this.security=security;
	}
	
	@Column(name="role")
	private Role role;
	
	public Role getRole(){
		return role;
	}
	
	public void setRole(Role role){
		this.role=role;
	}
	
}
