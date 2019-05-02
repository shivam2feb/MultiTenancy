package com.mfsi.appbuilder.service;

import java.util.List;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.APIDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;

public interface PersistenceService {

	public void saveProject(ProjectDTO projectDTO);
	public List<Project> getProject(String projectName);
	public List<Project> getAllProjects();
	public void createAPI(APIDTO apiDTO);
	public List<API> getAPI(String projectId);
}
