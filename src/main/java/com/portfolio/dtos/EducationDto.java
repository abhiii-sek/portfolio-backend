package com.portfolio.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EducationDto {

    private String educationId;

    @NotBlank(message = "School name cant be null")
    private String school;

    @NotBlank(message = "degree cannot be null")
    private String degree;

    @Min(value = 1900, message = "Invalid start year")
    private int from;

    @Min(value = 1900, message = "Invalid end year")
    private int to;

    @DecimalMin(value = "0.0", message = "Percentage must be at least 0")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private double percentage;
}
