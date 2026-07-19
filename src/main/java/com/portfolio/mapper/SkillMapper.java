package com.portfolio.mapper;

import com.portfolio.dtos.SkillDto;
import com.portfolio.entities.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SkillMapper extends BaseMapper<SkillDto, Skill> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skillId", ignore = true)
    void updateEntity(SkillDto dto, @MappingTarget Skill entity);
}