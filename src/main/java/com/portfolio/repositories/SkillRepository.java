package com.portfolio.repositories;

import com.portfolio.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    List<Skill> findByCategory(String category);

    boolean existsBySkillId(String skillId);

    Optional<Skill> findBySkillId(String skillId);
}