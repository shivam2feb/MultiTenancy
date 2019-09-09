package com.mfsi.appbuilder.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.MetaDataDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.dto.TableDetailsDTO;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.master.document.Project;
import com.mfsi.appbuilder.master.repository.APIRepository;
import com.mfsi.appbuilder.master.repository.ProjectRepository;
import com.mfsi.appbuilder.multitenant.config.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.mfsi.appbuilder.multitenant.config.TenantContextHolder;

@Service
public class PersistenceServiceImpl implements PersistenceService {

	private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	APIRepository apiRepository;

	@Autowired
	@Qualifier(value="datasourceBasedMultitenantConnectionProvider")
	private DataSourceBasedMultiTenantConnectionProviderImpl multiTenantDataSorce;


	public Project saveProject(ProjectDTO projectDTO) {
		Project project = new Project();
		BeanUtils.copyProperties(projectDTO, project);
		return projectRepository.save(project);
	}

	@Override
	public List<Project> getProject(String userId) {

		return projectRepository.findByUserId(userId);
	}

	public List<Project> getAllProjects() {
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
		api.setReJson(apiDTO.getReJson());
		apiRepository.save(api);
	}


	@Override
	public Project getProjectDetails(String projectId) {
		return projectRepository.findById(projectId).get();
	}

	/**
	 * This method returns a map
	 * with Table Name as key
	 * List of column names as value
	 *
	 * @param projectDTO
	 * @return Map<String   ,       List   <   String>>
	 */
	@Override
	public Map<String, List<MetaDataDTO>> getDBInfo(ProjectDTO projectDTO) {
		Map<String, List<MetaDataDTO>> metaData = new HashMap<>();
		List<MetaDataDTO> columns = null;

		try (Connection conn = multiTenantDataSorce.getConnection(TenantContextHolder.getTenantId());
				ResultSet resultSet = conn.getMetaData().getColumns(projectDTO.getDbDetailsDTO().getSchema(), null, "%", "%");){

			while (resultSet.next()) {
				columns = metaData.get(resultSet.getString(3));
				if (columns == null) {
					columns = new ArrayList<>();
					columns.add(new MetaDataDTO(resultSet.getString(4), resultSet.getString(6)));
					metaData.put(resultSet.getString(3), columns);
				} else {
					columns.add(new MetaDataDTO(resultSet.getString(4), resultSet.getString(6)));
					metaData.put(resultSet.getString(3), columns);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return metaData;
	}

	/**
	 * This method connects with the DB
	 * with provided credentials
	 * and returns a Connection Object
	 *
	 * @param url
	 * @param username
	 * @param password
	 * @return Connection
	 * @author Nayan
	 */
	@Override
	public Connection getMySqlConnection(String url, String username, String password) {
		Connection conn = null;
		try {
			Class.forName(SQL_DRIVER);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	@Override
	public void deleteApi(String apiId) {
		// TODO Auto-generated method stub
		apiRepository.deleteById(apiId);
	}

	@Override
	public void updateAPI(ApiDto apiDTO) {
		// TODO Auto-generated method stub
		API api = new API();
		api.setId(apiDTO.getId());
		api.setApiName(apiDTO.getApiName());
		api.setApiType(apiDTO.getApiType());
		api.setJsonString(apiDTO.getJsonString());
		api.setProjectId(apiDTO.getProjectId());
		api.setProjectName(apiDTO.getProjectName());
		api.setMainEntityIdType(apiDTO.getMainEntityIdType());
		api.setMainEntityName(apiDTO.getMainEntityName());
		api.setApiUrl(apiDTO.getApiUrl());
		api.setGetParams(apiDTO.getGetParams());
		api.setReJson(apiDTO.getReJson());
		apiRepository.save(api);

	}

	@Override
	public Map<String, List<MetaDataDTO>> createTable(TableDetailsDTO tableDetailsDTO) {			
		Connection conn = null;
		Statement statement = null;
		Boolean flag = false;
		StringBuilder query = new StringBuilder("CREATE TABLE ");
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setDbDetailsDTO(tableDetailsDTO.getDbDetailsDTO());
		try {
			List<MetaDataDTO> columnList = tableDetailsDTO.getMetaDataDTOs();
			query.append(tableDetailsDTO.getTableName()).append(" ( ");
			for (MetaDataDTO column : columnList) {
				if (flag) {
					query.append(", ");
				}
				query.append(column.getColumnName()).append(" ").append(column.getDataType());
				flag = true;

			}
			query.append(")");
			System.out.println(query.toString());

			conn = multiTenantDataSorce.getConnection(TenantContextHolder.getTenantId());
			statement = conn.createStatement();
			statement.executeUpdate(query.toString());
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDBInfo(projectDTO);
	}

	@Override
	public void deleteProject(String id) {
		projectRepository.deleteById(id);
	}

}
