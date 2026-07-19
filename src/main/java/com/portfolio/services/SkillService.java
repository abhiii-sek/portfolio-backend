package com.portfolio.services;

import com.portfolio.dtos.SkillDto;
import com.portfolio.entities.Skill;

import java.util.List;

public interface SkillService {

    public List<Skill> getAll();

    public Skill addSkill(SkillDto skillDto);

    public void removeSkill(String skillId);

    public Skill updateSkill(String skillId, SkillDto skillDto);
}
