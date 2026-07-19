package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "skills")
public class Skill extends BaseModel {

    private String skillId;

    private String category;
    private String name;
    private int proficiency = 80;

    public Skill() {}

    public Skill(String category, String name) {
        this.category = category;
        this.name = name;
    }

    public Skill(String category, String name, int proficiency) {
        this.category = category;
        this.name = name;
        this.proficiency = proficiency;
    }

}
