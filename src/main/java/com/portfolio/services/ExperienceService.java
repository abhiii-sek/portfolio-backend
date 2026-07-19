package com.portfolio.services;

import com.portfolio.dtos.ExperienceDto;
import com.portfolio.entities.Experience;

import java.util.List;

public interface ExperienceService {

    public List<Experience> getAll();

    public Experience addExperience(ExperienceDto experienceDto);

    public void removeExperience(String experienceId);

    public Experience updateExperience(String experienceId, ExperienceDto experienceDto);
}
