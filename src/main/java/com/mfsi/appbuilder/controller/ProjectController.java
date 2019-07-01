package com.mfsi.appbuilder.controller;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.service.PersistenceService;
import com.mfsi.appbuilder.util.AppBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

	@Autowired
	PersistenceService persistenceService;

    @GetMapping("/byName")
    private List<Project> getProject(Principal principal) {
        return persistenceService.getProject(AppBuilderUtil.getLoggedInUserId());
	}

	@PostMapping("/create")
	private Project createProject(@RequestBody ProjectDTO projectDTO, Principal principal) throws Exception {
        projectDTO.setUserId(AppBuilderUtil.getLoggedInUserId());
		if (persistenceService.getMySqlConnection(projectDTO.getDbURL(), projectDTO.getDbUsername()
				, projectDTO.getDbPassword()) == null)
			projectDTO.setVerified(false);
		else
			projectDTO.setVerified(true);
		return persistenceService.saveProject(projectDTO);
	}

	@GetMapping("/getAll")
	private List<Project> getAllProjects() {
		return persistenceService.getAllProjects();
	}
	
	@GetMapping("/getApis/{id}")
	private List<API> getApiDetails(@PathVariable String id) {
		return persistenceService.getAPI(id);
	}
	
	
	
	
	@GetMapping("/byProjectName/{projectName}")
	private Project getProjectDetails(@PathVariable String projectName) {
		return persistenceService.getProjectDetails(projectName);
	}


	@PostMapping("/createAPI")
	private void createAPI(@RequestBody ApiDto apiDTO) {
		persistenceService.createAPI(apiDTO);
	}
	
	@GetMapping("/getApiByProjectId/{projectId}")
	private List<API> getAPI(@PathVariable String projectId) {
		return persistenceService.getAPI(projectId);
	}
	
	/*
	 * @GetMapping("/getApiByProjectId/{projectId}") private List<API>
	 * getAPI(@PathVariable String projectId) { return
	 * persistenceService.getAPI(projectId); }
	 */
}