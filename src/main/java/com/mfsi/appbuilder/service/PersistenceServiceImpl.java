package com.mfsi.appbuilder.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.document.Project;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.repository.ProjectRepository;

@Service
public class PersistenceServiceImpl implements PersistenceService{

	@Autowired
	ProjectRepository projectRepository;
	
	public void saveProject(ProjectDTO projectDTO) {
		Project project=new Project();
		project.setProjectName(projectDTO.getProjectName());
		projectRepository.save(project);
	}

	@Override
	public Project getProject(String projectName) {
		return projectRepository.findByProjectName(projectName);
	}
	
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}
}
