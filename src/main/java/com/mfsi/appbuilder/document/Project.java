package com.mfsi.appbuilder.document;

import com.mfsi.appbuilder.dto.User;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document
public class Project {
	
	@Id
	private String id;
	
	private String projectName;
	private List<Map<String,String>> apiInfo=new ArrayList<>();
    @LastModifiedDate
    private Date lastUpdatedOn;
	private User user;
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Map<String, String>> getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(List<Map<String, String>> apiInfo) {
		this.apiInfo = apiInfo;
	}

    public String getLastUpdatedOn() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        System.out.println(sdf.format(lastUpdatedOn));
        return sdf.format(lastUpdatedOn);
	}

    public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
}