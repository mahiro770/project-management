package com.github.mahiro.projectmanager;

import java.sql.Timestamp;
import java.util.List;

public class ProjectService {

    private final ProjectRepository repo = new ProjectRepository();

    // 案件一覧取得
    public List<Project> getProjectList() {
        return repo.findAll();
    }

    // 案件詳細取得
    public Project getProjectDetail(int id) {

        if (id <= 0) {
            System.out.println("案件番号は1以上を入力してください。");
            return null;
        }

        Project project = repo.findById(id);

        if (project == null) {
            System.out.println("該当する案件が存在しません。");
        }

        return project;
    }

    // 案件登録
    public void registerProject(String title,
                                String clientName,
                                String skills,
                                String location,
                                String min,
                                String max) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("タイトルは必須です。");
        }

        if (clientName == null || clientName.isBlank()) {
            throw new IllegalArgumentException("会社名は必須です。");
        }

        Integer priceMin = min.isBlank() ? null : Integer.parseInt(min);
        Integer priceMax = max.isBlank() ? null : Integer.parseInt(max);

        Project project = new Project(
                null,
                title,
                clientName,
                skills,
                location,
                priceMin,
                priceMax,
                "OPEN",
                null,
                null);

        repo.insert(project);

        System.out.println("案件を登録しました。");
    }

    // 案件更新
    public void updateProject(int id,
                              String newTitle,
                              String newClientName,
                              String newSkills,
                              String newLocation,
                              String inputPriceMin,
                              String inputPriceMax,
                              String newStatus) {

        Project project = repo.findById(id);

        if (project == null) {
            System.out.println("案件が存在しません。");
            return;
        }

        boolean updateTitle = !newTitle.isBlank();
        if (updateTitle) {
            project.setTitle(newTitle);
        }

        boolean updateClientName = !newClientName.isBlank();
        if (updateClientName) {
            project.setClientName(newClientName);
        }

        boolean updateSkills = !newSkills.isBlank();
        if (updateSkills) {
            project.setRequiredSkills(newSkills);
        }

        boolean updateLocation = !newLocation.isBlank();
        if (updateLocation) {
            project.setLocation(newLocation);
        }

        boolean updatePriceMin = !inputPriceMin.isBlank();
        if (updatePriceMin) {
            project.setPriceMin(Integer.parseInt(inputPriceMin));
        }

        boolean updatePriceMax = !inputPriceMax.isBlank();
        if (updatePriceMax) {
            project.setPriceMax(Integer.parseInt(inputPriceMax));
        }

        boolean updateStatus = !newStatus.isBlank();
        if (updateStatus) {
            project.setStatus(newStatus);
        }

        project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        repo.update(
                project,
                updateTitle,
                updateClientName,
                updateSkills,
                updateLocation,
                updatePriceMin,
                updatePriceMax,
                updateStatus);

        System.out.println("更新が完了しました。");
    }

    // 案件削除
    public void deleteProject(int id) {

        Project project = repo.findById(id);

        if (project == null) {
            System.out.println("案件が見つかりません。");
            return;
        }

        if (repo.delete(id)) {
            System.out.println("削除が完了しました。");
        } else {
            System.out.println("削除に失敗しました。");
        }
    }
}
