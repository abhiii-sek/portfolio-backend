package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Entity
@Table(name = "experiences")
public class Experience extends BaseModel{
    private String experienceId;
    private String title;
    private String company;
    private String position;
    private String startDate;
    private String endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "experience_technologies", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "technology")
    private List<String> technologies;

    private String period;

}
