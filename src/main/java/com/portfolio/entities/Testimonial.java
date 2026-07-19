package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "testimonials")
public class Testimonial extends BaseModel {

    private String testimonialId;

    @Column(columnDefinition = "TEXT")
    private String quote;
    
    private String name;
    private String position;
    private String company;
}
