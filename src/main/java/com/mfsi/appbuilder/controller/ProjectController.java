package com.mfsi.appbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.service.PersistenceService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	PersistenceService persistenceService;

	@GetMapping("/byName/{projectName}")
	private Project getProject(@PathVariable String projectName) {
		return persistenceService.getProject(projectName);
	}

	@PostMapping("/create")
	private void createProject(@RequestBody ProjectDTO projectDTO) {
		persistenceService.saveProject(projectDTO);
	}

	@GetMapping("/getAll")
	private List<Project> getAllProjects(@PathVariable String projectName) {
		return persistenceService.getAllProjects();
	}
}