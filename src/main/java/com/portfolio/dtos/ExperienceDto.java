package com.portfolio.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ExperienceDto {

    private String experienceId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company name is required")
    private String company;

    @NotBlank(message = "Position is required")
    private String position;

    @NotBlank(message = "Start date is required")
    private String startDate;

    @NotBlank(message = "End date is required")
    private String endDate;

    @NotBlank(message = "Description is required")
    private String description;

    @NotEmpty(message = "At least one technology is required")
    private List<@NotBlank(message = "Technology name cannot be blank") String> technologies;

    private String period;
}
