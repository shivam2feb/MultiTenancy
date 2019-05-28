package com.app.AddUser.entity;

import java.util.*;
import javax.persistence.*;
import com.app.entity.*;

@Entity(name="Role")
public class Role {
	
	@Column(name="id")
	private Long id;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	@Column(name="name")
	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
}
