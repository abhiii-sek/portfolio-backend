package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "projects")
public class Project extends BaseModel {

    private String projectId;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String category;
    
    private String url;

    private String image;
    private String googlePlayUrl;
    private String appStoreUrl;
    private String websiteUrl;

    @Column(columnDefinition = "TEXT")
    private String problem;

    @Column(columnDefinition = "TEXT")
    private String solution;

    @Column(columnDefinition = "TEXT")
    private String result;

}
