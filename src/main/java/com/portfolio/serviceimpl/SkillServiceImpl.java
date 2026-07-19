package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.SkillDto;
import com.portfolio.entities.Skill;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.SkillMapper;
import com.portfolio.repositories.SkillRepository;
import com.portfolio.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private SkillMapper skillMapper;

    @Override
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill addSkill(SkillDto skillDto) {
        Skill skill = skillMapper.toEntity(skillDto);
        skill.setSkillId(Utils.generateUniqueId("Skill", skillRepository::existsBySkillId));
        return skillRepository.save(skill);
    }

    @Override
    public void removeSkill(String skillId) {
        Skill skill = skillRepository.findBySkillId(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid skillId"));
        skillRepository.delete(skill);
    }

    @Override
    public Skill updateSkill(String skillId, SkillDto skillDto) {
        Skill skill = skillRepository.findBySkillId(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid skillId"));
        skillMapper.updateEntity(skillDto, skill);
        return skillRepository.save(skill);
    }
}
