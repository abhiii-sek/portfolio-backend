package com.portfolio.services;

import com.portfolio.dtos.EducationDto;
import com.portfolio.entities.Education;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface EducationService {

    public List<Education> getAll();

    public Education addEducation(EducationDto educationDto) throws BadRequestException;

    public void removeEducation(String educationId);

    public Education updateEducation(String educationId, EducationDto educationDto) throws BadRequestException;
}
