package com.portfolio.repositories;

import com.portfolio.entities.EasterMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EasterMessageRepository extends JpaRepository<EasterMessage, Long> {
}
