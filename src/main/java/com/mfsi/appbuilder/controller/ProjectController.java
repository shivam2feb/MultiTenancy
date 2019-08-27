package com.mfsi.appbuilder.controller;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.MetaDataDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.dto.TableDetailsDTO;
import com.mfsi.appbuilder.service.PersistenceService;
import com.mfsi.appbuilder.util.AppBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

	@Autowired
	private PersistenceService persistenceService;

	/**
	 * getting all projects created by a user
	 * author: shubham
	 * @param principal
	 * @return list of project object
	 */
	@GetMapping("/byName")
	public List<Project> getProject(Principal principal) {
		return persistenceService.getProject(AppBuilderUtil.getLoggedInUserId());
	}

	@PostMapping("/create")
	private Project createProject(@RequestBody ProjectDTO projectDTO, Principal principal) throws Exception {
        projectDTO.setUserId(AppBuilderUtil.getLoggedInUserId());
		Connection conn = persistenceService.getMySqlConnection(projectDTO.getDbDetailsDTO().getDbURL(), projectDTO.getDbDetailsDTO().getDbUsername()
				, projectDTO.getDbDetailsDTO().getDbPassword());
		if (conn == null)
			projectDTO.getDbDetailsDTO().setVerified(false);
		else {
			projectDTO.getDbDetailsDTO().setVerified(true);
			if(projectDTO.getWantSecurity())
				persistenceService.createMatcherTable(conn);
			conn.close();
		}
		return persistenceService.saveProject(projectDTO);
	}

	/**
	 * method to get all projects 
	 * author: shubham
	 * @return list of projects
	 */
	@GetMapping("/getAll")
	public List<Project> getAllProjects() {
		return persistenceService.getAllProjects();
	}

	@GetMapping("/getApis/{id}")
	public List<API> getApiDetails(@PathVariable String id) {
		return persistenceService.getAPI(id);
	}

	/**
	 * method to get all project using project name
	 * author: shubham
	 * @param projectName
	 * @return
	 */
	@GetMapping("/byProjectName/{projectName}")
	public Project getProjectDetails(@PathVariable String projectName) {
		return persistenceService.getProjectDetails(projectName);
	}

	/**
	 * method to create a new API for a project
	 * author: shubham
	 * @param apiDTO containing project id and api Json data
	 */
	@PostMapping("/createAPI")
	public void createAPI(@RequestBody ApiDto apiDTO) {
		System.out.println("inside create");
		persistenceService.createAPI(apiDTO);
	}
	
	/**
	 * method to create a new API for a project
	 * author: shubham
	 * @param apiDTO containing project id and api Json data
	 */
	@PostMapping("/updateAPI")
	public void updateAPI(@RequestBody ApiDto apiDTO) {
		System.out.println("inside update");
		persistenceService.updateAPI(apiDTO);
	}


	/**
	 * method for getting all apis within projects
	 * author: shubham
	 * @param projectId 
	 * @return list of API objects
	 */
	@GetMapping("/getApiByProjectId/{projectId}")
	public List<API> getAPI(@PathVariable String projectId) {
		return persistenceService.getAPI(projectId);
	}
	
	/**
	 * Method for permanently delete the api
	 * author: shubham
	 * @param api : the api object which is going to delete
	 */
	@PostMapping("/deleteApi")
	public void deleteAPI(@RequestBody ApiDto api) {
		
		persistenceService.deleteApi(api.getId());
	}

	/**
	 * Method for  delete the project
	 * author: nayan
	 *
	 * @param
	 */
	@DeleteMapping("/deleteProject/{projectId}")
	public void deleteProject(@PathVariable String projectId) {

		persistenceService.deleteProject(projectId);
	}

	@PostMapping("/createTable")
    public Map<String, List<MetaDataDTO>> createTable(@RequestBody TableDetailsDTO dto) {
        return persistenceService.createTable(dto);
	}

}