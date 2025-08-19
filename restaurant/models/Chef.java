package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Chef class representing a restaurant chef
 */
public class Chef extends User {
    private String specialization;
    private int experienceYears;
    private List<String> skills;
    private boolean isAvailable;

    public Chef(String id, String name, String email, String phone, String specialization, int experienceYears) {
        super(id, name, email, phone);
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.skills = new ArrayList<>();
        this.isAvailable = true;
    }

    @Override
    public String getRole() {
        return "CHEF";
    }

    // Getters
    public String getSpecialization() {
        return specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Setters
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Skill management
    public void addSkill(String skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    public void removeSkill(String skill) {
        skills.remove(skill);
    }

    public boolean hasSkill(String skill) {
        return skills.contains(skill);
    }

    @Override
    public String toString() {
        return "Chef{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experienceYears=" + experienceYears +
                ", skills=" + skills +
                ", isAvailable=" + isAvailable +
                '}';
    }
}