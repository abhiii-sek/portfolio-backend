package com.portfolio.services;

import com.portfolio.dtos.ProjectDto;
import com.portfolio.entities.Project;

import java.util.List;

public interface ProjectService {

    public List<Project> getAll();

    public Project addProject(ProjectDto projectDto);

    public void removeProject(String projectId);

    public Project updateProject(String projectId, ProjectDto projectDto);
}
