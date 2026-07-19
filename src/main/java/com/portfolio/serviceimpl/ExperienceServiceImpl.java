package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.ExperienceDto;
import com.portfolio.entities.Experience;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.ExperienceMapper;
import com.portfolio.repositories.ExperienceRepository;
import com.portfolio.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private ExperienceMapper experienceMapper;

    @Override
    public List<Experience> getAll() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience addExperience(ExperienceDto experienceDto) {
        Experience experience = experienceMapper.toEntity(experienceDto);
        experience.setExperienceId(Utils.generateUniqueId("Exp", experienceRepository::existsByExperienceId));
        return experienceRepository.save(experience);
    }

    @Override
    public void removeExperience(String experienceId) {
        Experience experience = experienceRepository.findByExperienceId(experienceId)
                .orElseThrow(()-> new ResourceNotFoundException("no resource found for this id"));
        experienceRepository.delete(experience);
    }

    @Override
    public Experience updateExperience(String experienceId, ExperienceDto experienceDto) {
        Experience experience = experienceRepository.findByExperienceId(experienceId)
                .orElseThrow(()-> new ResourceNotFoundException("no resource found for this id"));
        experienceMapper.updateEntity(experienceDto, experience);
        return experienceRepository.save(experience);
    }
}
