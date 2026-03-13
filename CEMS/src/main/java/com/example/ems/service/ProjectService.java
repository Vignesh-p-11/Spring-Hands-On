package com.example.ems.service;

import java.util.List;
import com.example.ems.dto.ProjectRequest;
import com.example.ems.model.Project;

public interface ProjectService {
    Project create(ProjectRequest request);
    List<Project> createAll(List<ProjectRequest> requests);
}