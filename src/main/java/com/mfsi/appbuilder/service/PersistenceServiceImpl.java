package com.mfsi.appbuilder.service;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.repository.APIRepository;
import com.mfsi.appbuilder.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceServiceImpl implements PersistenceService{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	APIRepository apiRepository;
	
	public void saveProject(ProjectDTO projectDTO) {
		Project project=new Project();
		project.setProjectName(projectDTO.getProjectName());
		project.setUserId(projectDTO.getUserId());
	
		projectRepository.save(project);
	}

	@Override
	public List<Project> getProject(String userId) {

		return projectRepository.findByUserId(userId);
	}
	
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}

	
	@Override
	public List<API> getAPI(String projectID) {
		
		return apiRepository.findByProjectId(projectID);
	}

	@Override
	public void createAPI(ApiDto apiDTO) {
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
