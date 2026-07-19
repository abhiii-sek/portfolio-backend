package com.portfolio.mapper;

import com.portfolio.dtos.ProjectDto;
import com.portfolio.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<ProjectDto, Project> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    void updateEntity(ProjectDto dto, @MappingTarget Project entity);
}