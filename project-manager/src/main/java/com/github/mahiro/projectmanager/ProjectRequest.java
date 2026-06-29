package com.github.mahiro.projectmanager;

public class ProjectRequest {
    private String title;
    private String clientName;
    private String requiredSkills;
    private String location;
    private Integer priceMin;
    private Integer priceMax;
    private String status;

    public String getTitle() { return title; }
    public String getClientName() { return clientName; }
    public String getRequiredSkills() { return requiredSkills; }
    public String getLocation() { return location; }
    public Integer getPriceMin() { return priceMin; }
    public Integer getPriceMax() { return priceMax; }
    public String getStatus() { return status; }
}
