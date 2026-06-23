package com.github.mahiro.projectmanager;
import java.util.List;
import java.sql.Timestamp;

public class ProjectService {
    private ProjectRepository repo = new ProjectRepository();

    // 案件一覧を取得するメソッドcase1
    public List<Project> getProjectList() {
        // ここに将来的に「検索条件」や「並び替え」などのロジックを追加可能
        return repo.findAll();
    }

    //案件詳細表示するメソッドcase2
   public void showProjectDetail(int id) {
    // 入力値自体が不正な場合のチェック
    if (id <= 0) {
        System.out.println("エラー: 案件番号は1以上の正の整数を入力してください。");
        return;
    }
    
    Project project = repo.findById(id);
    if (project == null) {
        System.out.println("エラー: 該当する案件が見つかりません。");
        return;
    }

    repo.showDetail(id);
}


    // 登録前のバリデーションと登録実行を担当case3
    public void registerProject(String title, String clientName, String skills, 
                                String location, String min, String max) {
        
        // 必須チェック（もし空なら例外を投げてMainに伝える）
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("タイトルは必須です。");
        }
        if (clientName == null || clientName.isEmpty()) {
            throw new IllegalArgumentException("会社名は必須です。");
        }

        // 数値変換（例外処理をここに集約）
        Integer priceMin = (min.isEmpty()) ? null : Integer.parseInt(min);
        Integer priceMax = (max.isEmpty()) ? null : Integer.parseInt(max);

        // データベースへの登録
        Project newProject = new Project(null, title, clientName, skills, location, 
                                         priceMin, priceMax, null, null, null);
        repo.insert(newProject);
        System.out.println("案件の登録が完了しました！");
    }

    public void updateProject(int id, String newTitle, String newClientName, 
                          String newSkills, String newLocation, 
                          String inputPriceMin, String inputPriceMax, String newStatus) {
    
    // 1. 存在チェック
    Project project = repo.findById(id);
    if (project == null) {
        System.out.println("エラー: ID " + id + " の案件は見つかりません。");
        return;
    }

    // 2. 更新フラグと値の設定
    // --- 統一フォーマットで各項目を処理 ---
    boolean updateTitle = !newTitle.isEmpty();
    if (updateTitle) project.setTitle(newTitle);

    boolean updateClientName = !newClientName.isEmpty();
    if (updateClientName) project.setClientName(newClientName);

    boolean updateRequiredSkills = !newSkills.isEmpty();
    if (updateRequiredSkills) project.setRequiredSkills(newSkills);

    boolean updateLocation = !newLocation.isEmpty();
    if (updateLocation) project.setLocation(newLocation);

    boolean updatePriceMin = !inputPriceMin.isEmpty();
    if (updatePriceMin) project.setPriceMin(Integer.parseInt(inputPriceMin));

    boolean updatePriceMax = !inputPriceMax.isEmpty();
    if (updatePriceMax) project.setPriceMax(Integer.parseInt(inputPriceMax));

    boolean updateStatus = !newStatus.isEmpty();
    if (updateStatus) project.setStatus(newStatus);

    // 最後にDB更新を実行
    project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    repo.update(project, updateTitle, updateClientName, updateRequiredSkills, 
                updateLocation, updatePriceMin, updatePriceMax, updateStatus);
    
    System.out.println("更新が完了しました！");
}

// ProjectService.java に追加
public void deleteProject(int id) {
    // 存在チェック
    Project project = repo.findById(id);
    if (project == null) {
        System.out.println("指定された案件（ID: " + id + "）は見つかりませんでした。");
        System.out.println("一覧表示でIDを確認してください。");
        return;
    }

    // 削除実行
    if (repo.delete(id)) {
        System.out.println("削除が完了しました。");
    } else {
        System.out.println("削除中にエラーが発生しました。");
    }
}
}
