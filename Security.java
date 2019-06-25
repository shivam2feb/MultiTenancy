package com.app.AddUser.entity;

import java.util.*;
import javax.persistence.*;
import com.app.entity.*;

@Entity(name="Security")
public class Security {
	
	@Column(name="id")
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Column(name="password")
	private String password;
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
}
