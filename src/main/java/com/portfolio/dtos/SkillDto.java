package com.portfolio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillDto {

    private String skillId;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Skill name is required")
    private String name;
}