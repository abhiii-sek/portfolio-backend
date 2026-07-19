package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.PersonalInfoDto;
import com.portfolio.entities.PersonalInfo;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.PersonalInfoMapper;
import com.portfolio.repositories.PersonalInfoRepository;
import com.portfolio.services.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;
    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    @Override
    public List<PersonalInfo> getAll() {
        return personalInfoRepository.findAll();
    }

    @Override
    public PersonalInfo addPersonalInfo(PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = personalInfoMapper.toEntity(personalInfoDto);
        personalInfo.setPersonalId(Utils.generateUniqueId("My", personalInfoRepository::existsByPersonalId));
        return personalInfoRepository.save(personalInfo);
    }

    @Override
    public void removePersonalInfo(String personalId) {
        PersonalInfo personalInfo = personalInfoRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid personalId"));
        personalInfoRepository.delete(personalInfo);
    }

    @Override
    public PersonalInfo updatePersonalInfo(String personalId, PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = personalInfoRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid personalId"));
        personalInfoMapper.updateEntity(personalInfoDto, personalInfo);
        return personalInfoRepository.save(personalInfo);
    }
}
