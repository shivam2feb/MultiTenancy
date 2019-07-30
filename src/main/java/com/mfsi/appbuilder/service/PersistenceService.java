package com.mfsi.appbuilder.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;

public interface PersistenceService {

	public Project saveProject(ProjectDTO projectDTO);
	public List<Project> getProject(String projectName);
	public List<Project> getAllProjects();
	public void createAPI(ApiDto api);
	public List<API> getAPI(String projectId);
	public Project getProjectDetails(String projectId);

	public Map<String, List<String>> getDBInfo(ProjectDTO projectDTO);

	public Connection getMySqlConnection(String url, String username, String password) throws Exception;
	
	public void pushSecurityUrls(Project projectDetails, Set<String> securityUrls);
	public boolean createMatcherTable(Connection conn);
}
