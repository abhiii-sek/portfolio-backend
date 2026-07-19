package com.portfolio.repositories;

import com.portfolio.entities.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Integer> {

    boolean existsByPersonalId(String personalId);
    Optional<PersonalInfo> findByPersonalId(String personalId);
}
