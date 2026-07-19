package com.portfolio.services;

import com.portfolio.entities.VisitorAnalytic;

import java.util.List;
import java.util.Map;

public interface VisitorAnalyticService {

    public List<VisitorAnalytic> getAll();

    public void addAnalytic(Map<String, Object> payload);
}
