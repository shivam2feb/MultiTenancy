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

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    APIRepository apiRepository;

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
        apiRepository.save(api);
    }


    @Override
    public Project getProjectDetails(String projectId) {
        return projectRepository.findProjectBy_id(projectId);
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
    public Map<String, List<String>> getDBInfo(ProjectDTO projectDTO) {
        Map<String, List<String>> metaData = new HashMap<String, List<String>>();
        List<String> columns = null;
        DatabaseMetaData meta = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            conn = getMySqlConnection(projectDTO.getDbURL(), projectDTO.getDbUsername(), projectDTO.getDbPassword());
            if (null != conn) {
                throw new RuntimeException("Invalid DB Credentials");
            }
            meta = conn.getMetaData();
            resultSet = meta.getColumns(projectDTO.getSchema(), null, "%", "%");
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
}
