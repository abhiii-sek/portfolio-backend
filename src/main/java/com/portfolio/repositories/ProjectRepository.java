package com.portfolio.repositories;

import com.portfolio.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByProjectId(String projectId);

    Optional<Project> findByProjectId(String projectId);
}