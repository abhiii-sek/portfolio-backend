package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.ProjectDto;
import com.portfolio.entities.Project;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.repositories.ProjectRepository;
import com.portfolio.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getAll() {
       return projectRepository.findAll();
    }

    @Override
    public Project addProject(ProjectDto projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        project.setProjectId(Utils.generateUniqueId("Pro", projectRepository::existsByProjectId));
        return projectRepository.save(project);
    }

    @Override
    public void removeProject(String projectId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid projectId"));
        projectRepository.delete(project);
    }

    @Override
    public Project updateProject(String projectId, ProjectDto projectDto) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid projectId"));
        projectMapper.updateEntity(projectDto, project);
        return projectRepository.save(project);
    }
}
