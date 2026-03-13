package com.example.ems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ems.dto.ProjectRequest;
import com.example.ems.model.Project;
import com.example.ems.repository.ProjectRepository;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public Project create(ProjectRequest request) {
        Project p = new Project();
        p.setProjectId(request.getProjectId());
        p.setProjectName(request.getProjectName());
        return projectRepository.save(p);
    }

    @Override
    @Transactional
    public List<Project> createAll(List<ProjectRequest> requests) {
        List<Project> toSave = new ArrayList<>();
        for (ProjectRequest r : requests) {
            Project p = new Project();
            p.setProjectId(r.getProjectId());
            p.setProjectName(r.getProjectName());
            toSave.add(p);
        }
        return projectRepository.saveAll(toSave);
    }
}