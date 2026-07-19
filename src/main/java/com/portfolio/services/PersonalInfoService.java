package com.portfolio.services;

import com.portfolio.dtos.PersonalInfoDto;
import com.portfolio.entities.PersonalInfo;

import java.util.List;

public interface PersonalInfoService {

    public List<PersonalInfo> getAll();

    public PersonalInfo addPersonalInfo(PersonalInfoDto personalInfoDto);

    public void removePersonalInfo(String personalId);

    public PersonalInfo updatePersonalInfo(String personalId, PersonalInfoDto personalInfoDto);
}
