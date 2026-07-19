package com.portfolio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PersonalInfoDto {

    private String personalId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Tagline is required")
    private String tagline;

    @NotBlank(message = "LinkedIn username is required")
    private String linkedInUsername;

    private String resumeUrl;
    private String githubUsername;
    private String leetcodeUsername;
    private String instagramUsername;

    @Min(value = 0, message = "Years of experience cannot be negative")
    private int yearsExperience;

    @Min(value = 0, message = "Projects completed cannot be negative")
    private int projectsCompleted;

    @Min(value = 0, message = "Technologies count cannot be negative")
    private int technologies;
}
