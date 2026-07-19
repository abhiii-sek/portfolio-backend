package com.portfolio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestimonialDto {

    private String testimonialId;

    @NotBlank(message = "Quote is required")
    private String quote;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Position is required")
    private String position;

    @NotBlank(message = "Company is required")
    private String company;
}