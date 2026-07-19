package com.portfolio.repositories;

import com.portfolio.entities.VisitorAnalytic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorAnalyticRepository extends JpaRepository<VisitorAnalytic, Integer> {

    boolean existsByVisitorId(String visitorId);

    Optional<VisitorAnalytic> findByVisitorId(String visitorId);
}