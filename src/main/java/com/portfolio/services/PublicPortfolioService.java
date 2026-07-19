package com.portfolio.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.entities.*;
import com.portfolio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublicPortfolioService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Map<String, Object> getPortfolioData() {
        try {
            ClassPathResource resource = new ClassPathResource("data/portfolio-seed.json");
            Map<String, Object> root;
            try (InputStream is = resource.getInputStream()) {
                root = objectMapper.readValue(is, Map.class);
            }

            Map<String, Object> dynamicCvData = getDynamicCvData();
            root.put("cv_data", dynamicCvData);

            return root;
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct aggregated portfolio payload", e);
        }
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMetadata() {
        try {
            ClassPathResource resource = new ClassPathResource("data/portfolio-seed.json");
            Map<String, Object> root;
            try (InputStream is = resource.getInputStream()) {
                root = objectMapper.readValue(is, Map.class);
            }

            Map<String, Object> cvData = (Map<String, Object>) root.get("cv_data");
            if (cvData != null) {
                cvData.remove("personal_info");
                cvData.remove("experiences");
                cvData.remove("projects");
                cvData.remove("skills");
                cvData.remove("education");
                cvData.remove("testimonials");

                Map<String, Object> contactConfig = new LinkedHashMap<>();
                contactConfig.put("formspree_id", "DYNAMIC_SPRING_BACKEND");
                Map<String, String> formLabels = new LinkedHashMap<>();
                formLabels.put("name_label", "Name");
                formLabels.put("email_label", "Email");
                formLabels.put("message_label", "Message");
                formLabels.put("submit_button", "Send");
                contactConfig.put("form", formLabels);
                contactConfig.put("success_message", "Your message has been sent successfully!");
                contactConfig.put("error_message", "An error occurred. Please try again.");
                cvData.put("contact", contactConfig);
            }

            return root;
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct portfolio metadata", e);
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getPersonalInfo() {
        List<PersonalInfo> infos = personalInfoRepository.findAll();
        PersonalInfo personalInfo = infos.isEmpty() ? null : infos.get(0);
        if (personalInfo != null) {
            Map<String, Object> infoMap = new LinkedHashMap<>();
            infoMap.put("personalId", personalInfo.getPersonalId());
            infoMap.put("name", personalInfo.getName());
            infoMap.put("title", personalInfo.getTitle());
            infoMap.put("phone", personalInfo.getPhone());
            infoMap.put("email", personalInfo.getEmail());
            infoMap.put("location", personalInfo.getLocation());
            infoMap.put("bio", personalInfo.getBio());
            infoMap.put("tagline", personalInfo.getTagline());
            infoMap.put("linkedInUsername", personalInfo.getLinkedInUsername());
            infoMap.put("linkedin", personalInfo.getLinkedInUsername());
            infoMap.put("resumeUrl", personalInfo.getResumeUrl());
            infoMap.put("githubUsername", personalInfo.getGithubUsername());
            infoMap.put("github", personalInfo.getGithubUsername());
            infoMap.put("leetcodeUsername", personalInfo.getLeetcodeUsername());
            infoMap.put("leetcode", personalInfo.getLeetcodeUsername());
            infoMap.put("instagramUsername", personalInfo.getInstagramUsername());
            infoMap.put("instagram", personalInfo.getInstagramUsername());

            Map<String, Object> stats = new LinkedHashMap<>();
            stats.put("years_experience", personalInfo.getYearsExperience());
            stats.put("projects_completed", personalInfo.getProjectsCompleted());
            stats.put("technologies", personalInfo.getTechnologies());
            infoMap.put("stats", stats);

            infoMap.put("subtitles", Arrays.asList(personalInfo.getTitle(), "Flutter & Backend Developer"));

            return infoMap;
        }
        return Collections.emptyMap();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        return experiences.stream().map(e -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("experienceId", e.getExperienceId());
            map.put("title", e.getTitle());
            map.put("company", e.getCompany());
            map.put("position", e.getPosition());
            map.put("start_date", e.getStartDate());
            map.put("end_date", e.getEndDate());
            map.put("description", e.getDescription());
            map.put("technologies", e.getTechnologies());
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("projectId", p.getProjectId());
            map.put("title", p.getTitle());
            map.put("description", p.getDescription());
            map.put("category", p.getCategory());
            map.put("image", p.getImage() != null ? p.getImage() : "assets/images/project_default.png");

            if (p.getUrl() != null && !p.getUrl().isEmpty()) {
                map.put("url", p.getUrl());
            } else {
                Map<String, String> urls = new LinkedHashMap<>();
                if (p.getGooglePlayUrl() != null) urls.put("google_play", p.getGooglePlayUrl());
                if (p.getAppStoreUrl() != null) urls.put("app_store", p.getAppStoreUrl());
                if (p.getWebsiteUrl() != null) urls.put("website", p.getWebsiteUrl());
                map.put("url", urls);
            }

            if (p.getProblem() != null || p.getSolution() != null || p.getResult() != null) {
                Map<String, String> caseStudy = new LinkedHashMap<>();
                caseStudy.put("problem", p.getProblem() != null ? p.getProblem() : "");
                caseStudy.put("solution", p.getSolution() != null ? p.getSolution() : "");
                caseStudy.put("result", p.getResult() != null ? p.getResult() : "");
                map.put("case_study", caseStudy);
            }

            return map;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEducation() {
        List<Education> educations = educationRepository.findAll();
        return educations.stream().map(e -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("educationId", e.getEducationId());
            map.put("school", e.getSchool());
            map.put("degree", e.getDegree());
            map.put("from", e.getFrom());
            map.put("to", e.getTo());
            map.put("percentage", e.getPercentage());
            map.put("year", e.getFrom() != null && e.getTo() != null 
                    ? e.getFrom() + " — " + e.getTo() 
                    : "");
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTestimonials() {
        List<Testimonial> testimonials = testimonialRepository.findAll();
        return testimonials.stream().map(t -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("testimonialId", t.getTestimonialId());
            map.put("quote", t.getQuote());
            map.put("name", t.getName());
            map.put("position", t.getPosition());
            map.put("company", t.getCompany());
            return map;
        }).collect(Collectors.toList());
    }

    private Map<String, Object> getDynamicCvData() {
        Map<String, Object> cvData = new LinkedHashMap<>();

        cvData.put("personal_info", getPersonalInfo());
        cvData.put("experiences", getExperiences());
        cvData.put("projects", getProjects());
        cvData.put("education", getEducation());
        cvData.put("skills", getSkills());
        cvData.put("testimonials", getTestimonials());

        Map<String, Object> contactConfig = new LinkedHashMap<>();
        contactConfig.put("formspree_id", "DYNAMIC_SPRING_BACKEND");
        Map<String, String> formLabels = new LinkedHashMap<>();
        formLabels.put("name_label", "Name");
        formLabels.put("email_label", "Email");
        formLabels.put("message_label", "Message");
        formLabels.put("submit_button", "Send");
        contactConfig.put("form", formLabels);
        contactConfig.put("success_message", "Your message has been sent successfully!");
        contactConfig.put("error_message", "An error occurred. Please try again.");
        cvData.put("contact", contactConfig);

        return cvData;
    }

    public String getResumeUrl() {
        return personalInfoRepository.findAll().stream()
                .findFirst()
                .map(PersonalInfo::getResumeUrl)
                .orElse("");
    }
}
