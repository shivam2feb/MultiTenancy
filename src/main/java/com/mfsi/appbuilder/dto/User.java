package com.mfsi.appbuilder.dto;

public class User {
	private String userId;
	private String userName   ;
	private String userDOB ;
	public String getUserName() {
		return userName;
	}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDOB() {
		return userDOB;
	}
	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}
	public User(String userId, String userName, String userDOB) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userDOB = userDOB;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
