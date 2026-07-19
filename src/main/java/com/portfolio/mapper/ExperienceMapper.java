package com.portfolio.mapper;

import com.portfolio.dtos.ExperienceDto;
import com.portfolio.entities.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExperienceMapper extends BaseMapper<ExperienceDto, Experience> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "experienceId", ignore = true)
    void updateEntity(ExperienceDto dto, @MappingTarget Experience entity);
}