package com.example.ems.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.ProjectRequest;
import com.example.ems.dto.ProjectResponse;
import com.example.ems.model.Project;
import com.example.ems.repository.ProjectRepository;
import com.example.ems.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectRepository projectRepository;

	private final ProjectService projectService; // new

	public ProjectController(ProjectRepository projectRepository, ProjectService projectService) {
		this.projectRepository = projectRepository;
		this.projectService = projectService;
	}
	@GetMapping
	public List<ProjectResponse> all() {
		return projectRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	private ProjectResponse toResponse(Project p) {
		ProjectResponse dto = new ProjectResponse();
		dto.setProjectId(p.getProjectId());
		dto.setProjectName(p.getProjectName());
		return dto;
	}

	// NEW: create one project
	@PostMapping
	public ResponseEntity<Project> create(@Valid @RequestBody ProjectRequest request) {
		return ResponseEntity.ok(projectService.create(request));
	}

	// NEW: bulk create projects
	@PostMapping("/bulk")
	public ResponseEntity<List<Project>> createBulk(@Valid @RequestBody List<ProjectRequest> requests) {
		return ResponseEntity.ok(projectService.createAll(requests));
	}

}