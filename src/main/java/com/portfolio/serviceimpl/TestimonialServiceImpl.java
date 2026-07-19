package com.portfolio.serviceimpl;

import com.portfolio.common.Utils;
import com.portfolio.dtos.TestimonialDto;
import com.portfolio.entities.Testimonial;
import com.portfolio.exceptions.ResourceNotFoundException;
import com.portfolio.mapper.TestimonialMapper;
import com.portfolio.repositories.TestimonialRepository;
import com.portfolio.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;
    @Autowired
    private TestimonialMapper testimonialMapper;

    @Override
    public List<Testimonial> getAll() {
        return testimonialRepository.findAll();
    }

    @Override
    public Testimonial addTestimonial(TestimonialDto testimonialDto) {
        Testimonial testimonial = testimonialMapper.toEntity(testimonialDto);
        testimonial.setTestimonialId(Utils.generateUniqueId("Tes", testimonialRepository::existsByTestimonialId));
        return testimonialRepository.save(testimonial);
    }

    @Override
    public void removeTestimonial(String testimonialId) {
        Testimonial testimonial = testimonialRepository.findByTestimonialId(testimonialId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid testimonialId"));
        testimonialRepository.delete(testimonial);
    }

    @Override
    public Testimonial updateTestimonial(String testimonialId, TestimonialDto testimonialDto) {
        Testimonial testimonial = testimonialRepository.findByTestimonialId(testimonialId)
                .orElseThrow(() -> new ResourceNotFoundException("invalid testimonialId"));
        testimonialMapper.updateEntity(testimonialDto, testimonial);
        return testimonialRepository.save(testimonial);
    }
}
