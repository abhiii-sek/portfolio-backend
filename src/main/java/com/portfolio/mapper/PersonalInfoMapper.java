package com.portfolio.mapper;

import com.portfolio.dtos.PersonalInfoDto;
import com.portfolio.entities.PersonalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonalInfoMapper extends BaseMapper<PersonalInfoDto, PersonalInfo> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personalId", ignore = true)
    void updateEntity(PersonalInfoDto dto, @MappingTarget PersonalInfo entity);
}