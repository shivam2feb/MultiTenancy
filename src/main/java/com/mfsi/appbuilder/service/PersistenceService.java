package com.mfsi.appbuilder.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.MetaDataDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.dto.TableDetailsDTO;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.master.document.Project;

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
	
	public void updateAPI(ApiDto apiDTO);

	public void deleteProject(String id);

	public Map<String, List<MetaDataDTO>> createTable(TableDetailsDTO dto);
	public API findAPIByURL(String url);
	public API findAPIById(String id);
}
