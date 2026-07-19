package com.portfolio.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.entities.*;
import com.portfolio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/// DataSeeder reads the classpath resource portfolio-seed.json on startup 
/// to populate the MySQL tables if the database contains no personal info records.
@Component
public class DataSeeder implements CommandLineRunner {

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
    private UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        // Seed default Admin user if none exists
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@portfolio.com");
            admin.setPhoneNumber("1234567890");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(com.portfolio.enums.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Seeded default admin user: admin@portfolio.com / admin123");
        }

        boolean databaseEmpty = personalInfoRepository.count() == 0;

        // If not completely empty, check if specific tables need a re-seed due to missing fields
        if (!databaseEmpty) {
            boolean needsEduReseed = educationRepository.count() == 0 || 
                educationRepository.findAll().stream().anyMatch(e -> e.getFrom() == null || e.getFrom() == 0);
            if (needsEduReseed) {
                System.out.println("Educations need re-seeding due to missing from/to years.");
                educationRepository.deleteAll();
            }

            boolean needsProjReseed = projectRepository.count() == 0 || 
                projectRepository.findAll().stream().anyMatch(p -> p.getProblem() == null || p.getProblem().isEmpty() || p.getImage() == null || p.getImage().isEmpty());
            if (needsProjReseed) {
                System.out.println("Projects need re-seeding due to missing case studies or images.");
                projectRepository.deleteAll();
            }

            // If we did not delete anything and personalInfo exists, we can skip
            if (!needsEduReseed && !needsProjReseed) {
                System.out.println("Database already seeded with complete records. Skipping seeder.");
                return;
            }
        }

        // 2. Load the JSON seed template using ClassPathResource
        ClassPathResource resource = new ClassPathResource("data/portfolio-seed.json");
        if (!resource.exists()) {
            System.out.println("Could not locate classpath resource: data/portfolio-seed.json. Seeder skipped.");
            return;
        }

        System.out.println("Seeding database using classpath resource: data/portfolio-seed.json");
        
        JsonNode rootNode;
        try (InputStream is = resource.getInputStream()) {
            rootNode = objectMapper.readTree(is);
        }
        
        JsonNode cvDataNode = rootNode.get("cv_data");
        if (cvDataNode == null) {
            System.out.println("cv_data key is missing in seed json");
            return;
        }

        // 3. Seed Personal Info
        if (personalInfoRepository.count() == 0) {
            JsonNode personalInfoNode = cvDataNode.get("personal_info");
            if (personalInfoNode != null) {
                PersonalInfo info = new PersonalInfo();
                info.setPersonalId(java.util.UUID.randomUUID().toString());
                info.setName(personalInfoNode.get("name").asText());
                info.setTitle(personalInfoNode.get("title").asText());
                info.setPhone(personalInfoNode.get("phone").asText());
                info.setEmail(personalInfoNode.get("email").asText());
                info.setLocation(personalInfoNode.get("location").asText());
                info.setBio(personalInfoNode.get("bio").asText());
                info.setTagline(personalInfoNode.get("tagline").asText());
                info.setLinkedInUsername(personalInfoNode.has("linkedin") ? personalInfoNode.get("linkedin").asText() : "");
                info.setResumeUrl(personalInfoNode.has("resumeUrl") ? personalInfoNode.get("resumeUrl").asText() : "");
                info.setGithubUsername(personalInfoNode.has("github") ? personalInfoNode.get("github").asText() : "");
                info.setLeetcodeUsername(personalInfoNode.has("leetcode") ? personalInfoNode.get("leetcode").asText() : "");
                info.setInstagramUsername(personalInfoNode.has("instagram") ? personalInfoNode.get("instagram").asText() : "");
                
                JsonNode statsNode = personalInfoNode.get("stats");
                if (statsNode != null) {
                    info.setYearsExperience(statsNode.has("years_experience") ? statsNode.get("years_experience").asInt() : 0);
                    info.setProjectsCompleted(statsNode.has("projects_completed") ? statsNode.get("projects_completed").asInt() : 0);
                    info.setTechnologies(statsNode.has("technologies") ? statsNode.get("technologies").asInt() : 0);
                }
                personalInfoRepository.save(info);
            }
        }

        // 4. Seed Experiences
        if (experienceRepository.count() == 0) {
            JsonNode experiencesNode = cvDataNode.get("experiences");
            if (experiencesNode != null && experiencesNode.isArray()) {
                for (JsonNode expNode : experiencesNode) {
                    Experience exp = new Experience();
                    exp.setExperienceId(java.util.UUID.randomUUID().toString());
                    exp.setTitle(expNode.get("title").asText());
                    exp.setCompany(expNode.get("company").asText());
                    exp.setPosition(expNode.get("position").asText());
                    exp.setStartDate(expNode.has("start_date") ? expNode.get("start_date").asText() : "");
                    exp.setEndDate(expNode.has("end_date") ? expNode.get("end_date").asText() : "");
                    exp.setPeriod(expNode.get("period").asText());
                    exp.setDescription(expNode.get("description").asText());
                    
                    List<String> techs = new ArrayList<>();
                    JsonNode techsNode = expNode.get("technologies");
                    if (techsNode != null && techsNode.isArray()) {
                        for (JsonNode tech : techsNode) {
                            techs.add(tech.asText());
                        }
                    }
                    exp.setTechnologies(techs);
                    experienceRepository.save(exp);
                }
            }
        }

        // 5. Seed Projects
        if (projectRepository.count() == 0) {
            JsonNode projectsNode = cvDataNode.get("projects");
            if (projectsNode != null && projectsNode.isArray()) {
                for (JsonNode projNode : projectsNode) {
                    Project proj = new Project();
                    proj.setProjectId(java.util.UUID.randomUUID().toString());
                    proj.setTitle(projNode.get("title").asText());
                    proj.setDescription(projNode.get("description").asText());
                    proj.setCategory(projNode.get("category").asText());
                    proj.setImage(projNode.has("image") ? projNode.get("image").asText() : "assets/images/project_default.png");

                    JsonNode urlNode = projNode.get("url");
                    if (urlNode != null) {
                        if (urlNode.isObject()) {
                            proj.setGooglePlayUrl(urlNode.has("google_play") ? urlNode.get("google_play").asText() : null);
                            proj.setAppStoreUrl(urlNode.has("app_store") ? urlNode.get("app_store").asText() : null);
                            proj.setWebsiteUrl(urlNode.has("website") ? urlNode.get("website").asText() : null);
                        } else {
                            proj.setUrl(urlNode.asText());
                        }
                    }

                    // Look for problem/solution/result at root level first
                    if (projNode.has("problem") || projNode.has("solution") || projNode.has("result")) {
                        proj.setProblem(projNode.has("problem") ? projNode.get("problem").asText() : "");
                        proj.setSolution(projNode.has("solution") ? projNode.get("solution").asText() : "");
                        proj.setResult(projNode.has("result") ? projNode.get("result").asText() : "");
                    } else {
                        JsonNode csNode = projNode.get("case_study");
                        if (csNode != null) {
                            proj.setProblem(csNode.has("problem") ? csNode.get("problem").asText() : "");
                            proj.setSolution(csNode.has("solution") ? csNode.get("solution").asText() : "");
                            proj.setResult(csNode.has("result") ? csNode.get("result").asText() : "");
                        }
                    }

                    projectRepository.save(proj);
                }
            }
        }

        // 6. Seed Educations
        if (educationRepository.count() == 0) {
            JsonNode educationsNode = cvDataNode.get("education");
            if (educationsNode != null && educationsNode.isArray()) {
                for (JsonNode eduNode : educationsNode) {
                    Education edu = new Education();
                    edu.setEducationId(java.util.UUID.randomUUID().toString());
                    edu.setSchool(eduNode.get("school").asText());
                    edu.setDegree(eduNode.has("degree") ? eduNode.get("degree").asText() : null);
                    edu.setFrom(eduNode.has("from") ? eduNode.get("from").asInt() : null);
                    edu.setTo(eduNode.has("to") ? eduNode.get("to").asInt() : null);
                    edu.setPercentage(eduNode.has("percentage") ? eduNode.get("percentage").asDouble() : 0.0);
                    educationRepository.save(edu);
                }
            }
        }

        // 7. Seed Skills
        if (skillRepository.count() == 0) {
            JsonNode skillsNode = cvDataNode.get("skills");
            if (skillsNode != null && skillsNode.isArray()) {
                for (JsonNode skillCatNode : skillsNode) {
                    String category = skillCatNode.get("category").asText();
                    JsonNode itemsNode = skillCatNode.get("items");
                    if (itemsNode != null && itemsNode.isArray()) {
                        for (JsonNode itemNode : itemsNode) {
                            Skill skill = new Skill(category, itemNode.asText());
                            skill.setSkillId(java.util.UUID.randomUUID().toString());
                            skillRepository.save(skill);
                        }
                    }
                }
            }
        }

        // 8. Seed Testimonials
        if (testimonialRepository.count() == 0) {
            JsonNode testimonialsNode = cvDataNode.get("testimonials");
            if (testimonialsNode != null && testimonialsNode.isArray()) {
                for (JsonNode testNode : testimonialsNode) {
                    Testimonial test = new Testimonial();
                    test.setTestimonialId(java.util.UUID.randomUUID().toString());
                    test.setQuote(testNode.get("quote").asText());
                    test.setName(testNode.get("name").asText());
                    test.setPosition(testNode.get("position").asText());
                    test.setCompany(testNode.get("company").asText());
                    testimonialRepository.save(test);
                }
            }
        }

        System.out.println("MySQL database seeding from classpath resource completed successfully!");
    }
}
