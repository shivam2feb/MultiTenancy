package com.mfsi.appbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.service.PersistenceService;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

	@Autowired
	PersistenceService persistenceService;

	@GetMapping("/byName/{userName}")
	private List<Project> getProject(@PathVariable String userName) {
		return persistenceService.getProject(userName);
	}

	@PostMapping("/create")
	private void createProject(@RequestBody ProjectDTO projectDTO) {
		persistenceService.saveProject(projectDTO);
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