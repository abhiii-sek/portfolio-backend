package com.portfolio.repositories;

import com.portfolio.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

    boolean existsByEducationId(String educationId);

    Optional<Education> findByEducationId(String educationId);
}
