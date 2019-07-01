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
	private PersistenceService persistenceService;

	@GetMapping("/byName")
	public List<Project> getProject(Principal principal) {
		return persistenceService.getProject(AppBuilderUtil.getLoggedInUserId());
	}

	@PostMapping("/create")
	public void createProject(@RequestBody ProjectDTO projectDTO, Principal principal) {
		projectDTO.setUserId(AppBuilderUtil.getLoggedInUserId());
		persistenceService.saveProject(projectDTO);
	}

	@GetMapping("/getAll")
	public List<Project> getAllProjects() {
		return persistenceService.getAllProjects();
	}

	@GetMapping("/getApis/{id}")
	public List<API> getApiDetails(@PathVariable String id) {
		return persistenceService.getAPI(id);
	}

	@GetMapping("/byProjectName/{projectName}")
	public Project getProjectDetails(@PathVariable String projectName) {
		return persistenceService.getProjectDetails(projectName);
	}

	@PostMapping("/createAPI")
	public void createAPI(@RequestBody ApiDto apiDTO) {
		persistenceService.createAPI(apiDTO);
	}

	@GetMapping("/getApiByProjectId/{projectId}")
	public List<API> getAPI(@PathVariable String projectId) {
		return persistenceService.getAPI(projectId);
	}
}