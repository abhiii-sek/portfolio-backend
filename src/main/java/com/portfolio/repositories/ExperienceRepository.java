package com.portfolio.repositories;

import com.portfolio.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    boolean existsByExperienceId(String experienceId);
    Optional<Experience> findByExperienceId(String experienceId);
}
