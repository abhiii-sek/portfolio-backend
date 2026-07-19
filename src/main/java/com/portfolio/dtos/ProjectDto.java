package com.portfolio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProjectDto {

    private String projectId;

    @NotBlank(message = "Project title is required")
    private String title;

    @NotBlank(message = "Project description is required")
    private String description;

    @NotBlank(message = "Project category is required")
    private String category;

    @Pattern(
            regexp = "^(https?://.*)?$",
            message = "Invalid URL"
    )
    private String url;

    private String image;
    private String googlePlayUrl;
    private String appStoreUrl;
    private String websiteUrl;

    @NotBlank(message = "Problem statement is required")
    private String problem;

    @NotBlank(message = "Solution is required")
    private String solution;

    @NotBlank(message = "Result is required")
    private String result;
}