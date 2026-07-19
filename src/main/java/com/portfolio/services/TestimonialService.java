package com.portfolio.services;

import com.portfolio.dtos.TestimonialDto;
import com.portfolio.entities.Testimonial;

import java.util.List;

public interface TestimonialService {

    List<Testimonial> getAll();

    public Testimonial addTestimonial(TestimonialDto testimonialDto);

    public void removeTestimonial(String testimonialId);

    public Testimonial updateTestimonial(String testimonialId, TestimonialDto testimonialDto);
}
