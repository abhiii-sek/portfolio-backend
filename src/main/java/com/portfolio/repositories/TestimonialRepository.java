package com.portfolio.repositories;

import com.portfolio.entities.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Integer> {

    boolean existsByTestimonialId(String testimonialId);

    Optional<Testimonial> findByTestimonialId(String testimonialId);
}