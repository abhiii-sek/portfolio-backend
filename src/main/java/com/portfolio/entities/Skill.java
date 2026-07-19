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

    public Skill() {}

    public Skill(String category, String name) {
        this.category = category;
        this.name = name;
    }

}
