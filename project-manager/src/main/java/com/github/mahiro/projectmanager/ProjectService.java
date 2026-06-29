package com.github.mahiro.projectmanager;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public List<Project> getProjectList() {
        return repo.findAll();
    }

    public Project getProjectDetail(int id) {
        return repo.findById(id);
    }

    public void registerProject(String title, String clientName, String skills,
                                String location, Integer priceMin, Integer priceMax) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("タイトルは必須です。");
        }
        if (clientName == null || clientName.isBlank()) {
            throw new IllegalArgumentException("会社名は必須です。");
        }

        Project project = new Project(null, title, clientName, skills, location,
                priceMin, priceMax, "OPEN", null, null);
        repo.insert(project);
    }

    public void updateProject(int id, String title, String clientName, String skills,
                              String location, Integer priceMin, Integer priceMax, String status) {
        Project project = repo.findById(id);
        if (project == null) {
            throw new IllegalArgumentException("案件が存在しません。");
        }

        if (title != null && !title.isBlank()) project.setTitle(title);
        if (clientName != null && !clientName.isBlank()) project.setClientName(clientName);
        if (skills != null) project.setRequiredSkills(skills);
        if (location != null) project.setLocation(location);
        if (priceMin != null) project.setPriceMin(priceMin);
        if (priceMax != null) project.setPriceMax(priceMax);
        if (status != null && !status.isBlank()) project.setStatus(status);

        repo.update(project);
    }

    public void deleteProject(int id) {
        if (!repo.delete(id)) {
            throw new IllegalArgumentException("案件が見つかりません。");
        }
    }
}
