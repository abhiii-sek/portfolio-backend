package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "educations")
@Data
public class Education extends  BaseModel{

    @Column(unique = true, nullable = false)
    private String educationId;

    private String school;

    private String degree;

    @Column(name = "from_year")
    private Integer from;

    @Column(name = "to_year")
    private Integer to;

    private double percentage;
}
