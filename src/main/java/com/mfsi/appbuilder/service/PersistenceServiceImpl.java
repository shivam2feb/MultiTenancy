package com.mfsi.appbuilder.service;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.repository.APIRepository;
import com.mfsi.appbuilder.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersistenceServiceImpl implements PersistenceService{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	APIRepository apiRepository;

	public Project saveProject(ProjectDTO projectDTO) {
		Project project=new Project();
		BeanUtils.copyProperties(projectDTO, project);
		return projectRepository.save(project);
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
		return projectRepository.findProjectById(projectId);
	}


	@Override
	public Map<String, List<String>> getDBInfo(ProjectDTO projectDTO) {
		Map<String, List<String>> metaData = new HashMap<String, List<String>>();
		List<String> columns = null;
		try {
			Connection conn = getMySqlConnection(projectDTO.getDbURL(), projectDTO.getDbUsername(), projectDTO.getDbPassword());
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet resultSet = meta.getColumns(projectDTO.getSchema(), null, "%", "%");
			while (resultSet.next()) {
				columns = metaData.get(resultSet.getString(3));
				if (columns == null) {
					columns = new ArrayList<String>();
					columns.add(resultSet.getString(4));
					metaData.put(resultSet.getString(3), columns);
				} else {
					columns.add(resultSet.getString(4));
					metaData.put(resultSet.getString(3), columns);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return metaData;
	}

	@Override
	public Connection getMySqlConnection(String url, String username, String password) {
		String driver = "com.mysql.jdbc.Driver";
//		url = "jdbc:mysql://localhost:3306/ems_dev";
//		username = "root";
//		password = "root";
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}
}
