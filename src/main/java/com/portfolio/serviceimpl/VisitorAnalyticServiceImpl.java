package com.portfolio.serviceimpl;

import com.portfolio.entities.VisitorAnalytic;
import com.portfolio.repositories.VisitorAnalyticRepository;
import com.portfolio.services.VisitorAnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class VisitorAnalyticServiceImpl implements VisitorAnalyticService {

    @Autowired
    private VisitorAnalyticRepository visitorAnalyticRepository;

    @Override
    public List<VisitorAnalytic> getAll() {
        return visitorAnalyticRepository.findAll();
    }

    @Override
    public void addAnalytic(Map<String, Object> payload) {
        VisitorAnalytic analytic = new VisitorAnalytic();
        analytic.setVisitorId((String) payload.get("visitor_id"));
        analytic.setIp((String) payload.get("ip"));
        analytic.setCity((String) payload.get("city"));
        analytic.setCountry((String) payload.get("country"));
        analytic.setRepeating((Boolean) payload.get("is_repeating"));
        analytic.setDeviceType((String) payload.get("device_type"));

        String tsString = (String) payload.get("timestamp");
        if (tsString != null) {
            try {
                analytic.setTimestamp(OffsetDateTime.parse(tsString).toLocalDateTime());
            } catch (Exception e) {
                analytic.setTimestamp(LocalDateTime.now());
            }
        } else {
            analytic.setTimestamp(LocalDateTime.now());
        }

        Object freqObj = payload.get("frequency");
        if (freqObj instanceof Number) {
            analytic.setFrequency(((Number) freqObj).intValue());
        } else {
            analytic.setFrequency(1);
        }

        visitorAnalyticRepository.save(analytic);
    }
}
