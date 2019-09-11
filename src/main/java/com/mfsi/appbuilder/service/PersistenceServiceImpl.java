package com.mfsi.appbuilder.service;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String ALTER_TABLE = "ALTER TABLE ";
    private static final String ADD_COLUMN = " ADD COLUMN";

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
        List<MetaDataDTO> columns;
        PreparedStatement statement;
        StringBuilder query = new StringBuilder();
        String tableName;

        try (Connection conn = multiTenantDataSorce.getConnection(TenantContextHolder.getTenantId())) {
            query.append("Select TABLE_NAME,COLUMN_NAME,DATA_TYPE,COLUMN_KEY from Information_schema.columns WHERE\n" +
                    "    TABLE_SCHEMA = ?");
            statement = conn.prepareStatement(query.toString());
            statement.setString(1, "ems_dev");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tableName = resultSet.getString("TABLE_NAME");
                columns = metaData.get(tableName);
                if (columns == null) {
                    columns = new ArrayList<>();
                    columns.add(new MetaDataDTO(resultSet.getString("COLUMN_NAME"), resultSet.getString("DATA_TYPE"), resultSet.getString("COLUMN_KEY")));
                    metaData.put(tableName, columns);
                } else {
                    columns.add(new MetaDataDTO(resultSet.getString("COLUMN_NAME"), resultSet.getString("DATA_TYPE"), resultSet.getString("COLUMN_KEY")));
                    //metaData.put(tableName, columns);
                }
            }
            statement.close();

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
        Connection conn;
        Statement statement;
        Boolean flag = false;
        StringBuilder query = new StringBuilder();

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setDbDetailsDTO(tableDetailsDTO.getDbDetailsDTO());
        try {
            List<MetaDataDTO> columnList = tableDetailsDTO.getMetaDataDTOs();
            if (tableDetailsDTO.isCreateFlow()) {
                query.append(CREATE_TABLE);
                query.append(tableDetailsDTO.getTableName());
            } else {
                query.append(ALTER_TABLE);
                query.append(tableDetailsDTO.getTableName());
                query.append(ADD_COLUMN);
            }
            query.append(" ( ");
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
