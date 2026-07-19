package com.portfolio.mapper;

import com.portfolio.dtos.EducationDto;
import com.portfolio.entities.Education;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EducationMapper extends BaseMapper<EducationDto, Education> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "educationId", ignore = true)
    void updateEntity(EducationDto dto, @MappingTarget Education entity);
}