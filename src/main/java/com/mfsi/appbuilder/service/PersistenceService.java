package com.mfsi.appbuilder.service;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.MetaDataDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.dto.TableDetailsDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PersistenceService {

	public Project saveProject(ProjectDTO projectDTO);
	public List<Project> getProject(String projectName);
	public List<Project> getAllProjects();
	public void createAPI(ApiDto api);
	
	public void deleteApi(String apiId);
	public List<API> getAPI(String projectId);
	public Project getProjectDetails(String projectId);

	public Map<String, List<MetaDataDTO>> getDBInfo(ProjectDTO projectDTO);

	public Connection getMySqlConnection(String url, String username, String password) throws Exception;

	
	public void pushSecurityUrls(Project projectDetails, Set<String> securityUrls);
	public boolean createMatcherTable(Connection conn);

	public void updateAPI(ApiDto apiDTO);

	public void deleteProject(String id);

	public MetaDataDTO createTable(TableDetailsDTO dto);
}
