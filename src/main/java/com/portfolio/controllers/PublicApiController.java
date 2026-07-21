package com.portfolio.controllers;

import com.portfolio.entities.ContactMessage;
import com.portfolio.entities.EasterMessage;
import com.portfolio.repositories.ContactMessageRepository;
import com.portfolio.repositories.EasterMessageRepository;
import com.portfolio.services.PublicPortfolioService;
import com.portfolio.services.VisitorAnalyticService;
import com.portfolio.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {

    @Autowired
    private PublicPortfolioService publicPortfolioService;

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private EasterMessageRepository easterMessageRepository;

    @Autowired
    private VisitorAnalyticService visitorAnalyticService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/portfolio/metadata")
    public ResponseEntity<Map<String, Object>> getMetadata() {
        return ResponseEntity.ok(publicPortfolioService.getMetadata());
    }

    @GetMapping("/portfolio/personal-info")
    public ResponseEntity<Map<String, Object>> getPersonalInfo() {
        return ResponseEntity.ok(publicPortfolioService.getPersonalInfo());
    }

    @GetMapping("/portfolio/experiences")
    public ResponseEntity<List<Map<String, Object>>> getExperiences() {
        return ResponseEntity.ok(publicPortfolioService.getExperiences());
    }

    @GetMapping("/portfolio/projects")
    public ResponseEntity<List<Map<String, Object>>> getProjects() {
        return ResponseEntity.ok(publicPortfolioService.getProjects());
    }

    @GetMapping("/portfolio/education")
    public ResponseEntity<List<Map<String, Object>>> getEducation() {
        return ResponseEntity.ok(publicPortfolioService.getEducation());
    }

    @GetMapping("/portfolio/skills")
    public ResponseEntity<List<Map<String, Object>>> getSkills() {
        return ResponseEntity.ok(publicPortfolioService.getSkills());
    }

    @GetMapping("/portfolio/testimonials")
    public ResponseEntity<List<Map<String, Object>>> getTestimonials() {
        return ResponseEntity.ok(publicPortfolioService.getTestimonials());
    }

    @GetMapping("/resume")
    public ResponseEntity<Void> getResume() {
        String resumeUrl = publicPortfolioService.getResumeUrl();
        if (resumeUrl == null || resumeUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
                .header(org.springframework.http.HttpHeaders.LOCATION, resumeUrl)
                .build();
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, Object>> submitContact(@RequestBody ContactMessage message) {
        contactMessageRepository.save(message);
        CompletableFuture.runAsync(() -> emailService.sendContactEmail(message));
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Message received successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/analytics")
    public ResponseEntity<Map<String, Object>> submitAnalytics(@RequestBody Map<String, Object> payload) {
        visitorAnalyticService.addAnalytic(payload);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/easter-add-messages")
    public ResponseEntity<Map<String, Object>> submitBirthdayWish(@RequestBody EasterMessage wish) {
        easterMessageRepository.save(wish);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Wish saved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/easter-send-email")
    public ResponseEntity<Map<String, Object>> sendEasterMessagesEmail() {
        List<EasterMessage> wishes = easterMessageRepository.findAll();
        CompletableFuture.runAsync(() -> emailService.sendEasterMessagesEmail(wishes));
        easterMessageRepository.deleteAll();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Easter messages email triggered and messages table cleared");
        return ResponseEntity.ok(response);
    }
}
