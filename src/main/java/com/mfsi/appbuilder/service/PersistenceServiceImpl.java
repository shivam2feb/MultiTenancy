package com.mfsi.appbuilder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.dto.User;
import com.mfsi.appbuilder.repository.APIRepository;
import com.mfsi.appbuilder.repository.ProjectRepository;

import org.json.*;

@Service
public class PersistenceServiceImpl implements PersistenceService{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	APIRepository apiRepository;
	
	public void saveProject(ProjectDTO projectDTO) {
		Project project=new Project();
		User user =new User();
		user.setUserId(projectDTO.getUser().getUserId());
		user.setUserName(projectDTO.getUser().getUserName());
		user.setUserDOB(projectDTO.getUser().getUserDOB());
		project.setProjectName(projectDTO.getProjectName());
		project.setUser(user);
	
		projectRepository.save(project);
	}

	@Override
	public List<Project> getProject(String userName) {
		
		return projectRepository.findByUserUserName(userName);
	}
	
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}

	
	@Override
	public List<API> getAPI(String projectID) {
		
		return apiRepository.findByProjectId(projectID);
	}
	
	
	

	@Override
	public List<Project> getProjects(String userName) {
		
		return projectRepository.findByUserUserName(userName);
	}
	


	@Override
	public void createAPI(ApiDto apiDTO) {
		// TODO Auto-generated method stub
		//System.out.println(apiDTO);
		API api = new API();
		api.setApiName(apiDTO.getApiName());
		api.setApiType(apiDTO.getApiType());
		api.setJsonString(apiDTO.getJsonString());
		api.setProjectId(apiDTO.getProjectId());
		api.setProjectName(apiDTO.getProjectName());
		api.setMainEntityIdType(apiDTO.getMainEntityIdType());
		api.setMainEntityName(apiDTO.getMainEntityName());
		api.setApiUrl(apiDTO.getApiUrl());
		api.setGetParams(apiDTO.getGetParams());
		apiRepository.save(api);
	}
	


	@Override
	public Project getProjectDetails(String projectId) {
		// TODO Auto-generated method stub
		return projectRepository.findProjectById(projectId);
	}
}
