package com.portfolio.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorAnalyticDto {

    private String visitorId;

    @NotBlank(message = "IP address is required")
    private String ip;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    private boolean repeating;

    @NotBlank(message = "Device type is required")
    private String deviceType;

    private LocalDateTime timestamp;

    @Min(value = 1, message = "Frequency must be at least 1")
    private int frequency;
}