package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.EducationDto;
import com.portfolio.entities.Education;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.EducationMapper;
import com.portfolio.repositories.EducationRepository;
import com.portfolio.services.EducationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private EducationMapper educationMapper;

    private void validateFromTo(Integer from, Integer to) throws BadRequestException {
        if (from != null && to != null && to < from) {
            throw new BadRequestException("End year cannot be before start year.");
        }
    }


    @Override
    public List<Education> getAll() {
        return educationRepository.findAll();
    }

    @Override
    public Education addEducation(EducationDto educationDto) throws  BadRequestException{
        Education education = educationMapper.toEntity(educationDto);
        validateFromTo(education.getFrom(), education.getTo());
        education.setEducationId(Utils.generateUniqueId(
                "EDU",
                educationRepository::existsByEducationId
        ));
        return  educationRepository.save(education);
    }

    @Override
    public void removeEducation(String educationId) {
        Education education = educationRepository.findByEducationId(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found"));
        educationRepository.delete(education);
    }

    @Override
    public Education updateEducation(String educationId, EducationDto educationDto) throws BadRequestException {
        Education education = educationRepository.findByEducationId(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found"));
        educationMapper.updateEntity(educationDto, education);
        validateFromTo(education.getFrom(), education.getTo());
        return educationRepository.save(education);
    }
}
