package com.mfsi.appbuilder.service;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;

import java.util.List;

public interface PersistenceService {

	public void saveProject(ProjectDTO projectDTO);
	public List<Project> getProject(String projectName);
	public List<Project> getAllProjects();
	public void createAPI(ApiDto api);
	public List<API> getAPI(String projectId);
	public Project getProjectDetails(String projectId);
}
