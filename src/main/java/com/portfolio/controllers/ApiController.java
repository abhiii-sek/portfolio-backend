package com.portfolio.controllers;

import com.portfolio.dtos.*;
import com.portfolio.entities.*;
import com.portfolio.services.*;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Validated
public class ApiController {

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private TestimonialService testimonialService;

    @Autowired
    private VisitorAnalyticService visitorAnalyticService;

    @GetMapping("/personal-info")
    public ResponseEntity<List<PersonalInfo>> getAllPersonalInfo() {
        return ResponseEntity.ok(personalInfoService.getAll());
    }

    @PostMapping("/personal-info")
    public ResponseEntity<PersonalInfo> addPersonalInfo(@Valid @RequestBody PersonalInfoDto personalInfoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personalInfoService.addPersonalInfo(personalInfoDto));
    }

    @PutMapping("/personal-info/{id}")
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@PathVariable String id, @Valid @RequestBody PersonalInfoDto personalInfoDto) {
        return ResponseEntity.ok(personalInfoService.updatePersonalInfo(id, personalInfoDto));
    }

    @DeleteMapping("/personal-info/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePersonalInfo(@PathVariable String id) {
        personalInfoService.removePersonalInfo(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAll());
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> addSkill(@Valid @RequestBody SkillDto skillDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addSkill(skillDto));
    }

    @PutMapping("/skills/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable String skillId, @Valid @RequestBody SkillDto skillDto) {
        return ResponseEntity.ok(skillService.updateSkill(skillId, skillDto));
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Map<String, Boolean>> deleteSkill(@PathVariable String skillId) {
        skillService.removeSkill(skillId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/experiences")
    public ResponseEntity<List<Experience>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAll());
    }

    @PostMapping("/experiences")
    public ResponseEntity<Experience> addExperience(@Valid @RequestBody ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.addExperience(experienceDto));
    }

    @PutMapping("/experiences/{experienceId}")
    public ResponseEntity<Experience> updateExperience(@PathVariable String experienceId, @Valid @RequestBody ExperienceDto experienceDto) {
        return ResponseEntity.ok(experienceService.updateExperience(experienceId, experienceDto));
    }

    @DeleteMapping("/experiences/{experienceId}")
    public ResponseEntity<Map<String, Boolean>> deleteExperience(@PathVariable String experienceId) {
        experienceService.removeExperience(experienceId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> addProject(@Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addProject(projectDto));
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable String projectId, @Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.updateProject(projectId, projectDto));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Map<String, Boolean>> deleteProject(@PathVariable String projectId) {
        projectService.removeProject(projectId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/educations")
    public ResponseEntity<List<Education>> getAllEducations() {
        return ResponseEntity.ok(educationService.getAll());
    }

    @PostMapping("/educations")
    public ResponseEntity<Education> addEducation(@Valid @RequestBody EducationDto educationDto) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(educationService.addEducation(educationDto));
    }

    @PutMapping("/educations/{educationId}")
    public ResponseEntity<Education> updateEducation(@PathVariable String educationId, @Valid @RequestBody EducationDto educationDto) throws BadRequestException {
        return ResponseEntity.ok(educationService.updateEducation(educationId, educationDto));
    }

    @DeleteMapping("/educations/{educationId}")
    public ResponseEntity<Map<String, Boolean>> deleteEducation(@PathVariable String educationId) {
        educationService.removeEducation(educationId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/testimonials")
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        return ResponseEntity.ok(testimonialService.getAll());
    }

    @PostMapping("/testimonials")
    public ResponseEntity<Testimonial> addTestimonial(@Valid @RequestBody TestimonialDto testimonialDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialService.addTestimonial(testimonialDto));
    }

    @PutMapping("/testimonials/{testimonialId}")
    public ResponseEntity<Testimonial> updateTestimonial(@PathVariable String testimonialId, @Valid @RequestBody TestimonialDto testimonialDto) {
        return ResponseEntity.ok(testimonialService.updateTestimonial(testimonialId, testimonialDto));
    }

    @DeleteMapping("/testimonials/{testimonialId}")
    public ResponseEntity<Map<String, Boolean>> deleteTestimonial(@PathVariable String testimonialId) {
        testimonialService.removeTestimonial(testimonialId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics")
    public ResponseEntity<List<VisitorAnalytic>> getAllVisitorAnalytics() {
        return ResponseEntity.ok(visitorAnalyticService.getAll());
    }
}
