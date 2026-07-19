package com.portfolio.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "visitor_analytics")
public class VisitorAnalytic extends BaseModel {

    private String visitorId;

    private String ip;
    private String city;
    private String country;

    @Column(name = "is_repeating")
    private boolean isRepeating;

    @Column(name = "device_type")
    private String deviceType;

    private LocalDateTime timestamp;
    private int frequency;
}
