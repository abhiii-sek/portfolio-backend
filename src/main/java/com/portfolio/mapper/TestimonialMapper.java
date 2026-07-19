package com.portfolio.mapper;

import com.portfolio.dtos.TestimonialDto;
import com.portfolio.entities.Testimonial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TestimonialMapper extends BaseMapper<TestimonialDto, Testimonial> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testimonialId", ignore = true)
    void updateEntity(TestimonialDto dto, @MappingTarget Testimonial entity);
}