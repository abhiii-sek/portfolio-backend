package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "personal_info")
public class PersonalInfo extends BaseModel {
    private String personalId;

    private String name;
    private String title;
    private String phone;
    private String email;
    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    private String tagline;
    private String linkedInUsername;
    private String resumeUrl;
    private String githubUsername;
    private String leetcodeUsername;
    private String instagramUsername;
    private int yearsExperience;
    private int projectsCompleted;
    private int technologies;
}
