package com.github.mahiro.projectmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLClientInfoException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectRepository {
    Scanner scan = new Scanner(System.in);

    //案件一覧表示
    public List<Project> findAll() {
        List<Project> list = new ArrayList<Project>();
        String sql = "SELECT * FROM projects ORDER BY id";

        try (Connection conn = Database.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql);
                ResultSet rs = stm.executeQuery()) {

            
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String clientName = rs.getString("client_name");
                String requiredSkills = rs.getString("required_skill");
                String location = rs.getString("location");
                Integer priceMin = rs.getInt("price_min");
                Integer priceMax = rs.getInt("price_max");
                String status = rs.getString("status");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                Project p = new Project(id, title, clientName, requiredSkills, location, priceMin, priceMax, status,
                        createdAt, updatedAt);

                list.add(p);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("案件情報を取得できませんでした");
        }
        return list;
    }

    //案件詳細表示
     public void showDetail(int select_id) {

        String sql = "SELECT * FROM projects WHERE id = ?";

        try (Connection conn = Database.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, select_id);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    // データを取得して、表示
                    System.out.println("ID:" + rs.getInt("id"));
                    System.out.println("案件名:" + rs.getString("title"));
                    System.out.println("会社名: " + rs.getString("client_name"));
                    System.out.println("スキル: " + rs.getString("required_skill"));
                    System.out.println("勤務地: " + rs.getString("location"));
                    System.out.println("最低金額: " + rs.getInt("price_min"));
                    System.out.println("最高金額: " + rs.getInt("price_max"));
                    System.out.println("作成日時:" + rs.getTimestamp("created_at"));
                    System.out.println("更新日時: " + rs.getTimestamp("updated_at"));
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.out.println("データの取得中にエラーが発生しました");
        }
    }

    //案件登録
    public void insert(Project project) {
        String sql = "INSERT INTO projects (title,client_name,required_skill,location,price_min,price_max) VALUES(?,?,?,?,?,?)";

        // try-with-resources でリソースを自動でクローズ
        try (Connection conn = Database.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

            // 各値をセット
            stm.setString(1, project.getTitle());
            stm.setString(2, project.getClientName());
            stm.setString(3, project.getRequiredSkills());
            stm.setString(4, project.getLocation());

            if (project.getPriceMin() != null) {
                stm.setInt(5, project.getPriceMin());
            } else {
                stm.setNull(5, java.sql.Types.INTEGER);
            }

            if (project.getPriceMax() != null) {
                stm.setInt(6, project.getPriceMax());
            } else {
                stm.setNull(6, java.sql.Types.INTEGER);
            }

            stm.executeUpdate();
            System.out.println("登録が完了しました");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登録に失敗しました:" + e.getMessage());
        }

    }

    
    //案件更新
    public void update(Project project, boolean updateTitle, boolean updateClient_name, boolean updateRequired_skill, boolean updateLocation, boolean updatePrice_min, boolean updatePrice_max,boolean updateStatus){

     List<String> setClauses = new ArrayList<>();
     List<Object> values = new ArrayList<>();

     if(updateTitle){
        setClauses.add("title = ?");
        values.add(project.getTitle());
     }
     if(updateClient_name){
        setClauses.add("client_name = ?");
        values.add(project.getClientName());
     }
     if(updateRequired_skill){
        setClauses.add("required_skill = ?");
        values.add(project.getRequiredSkills());
     }
     if(updateLocation){
        setClauses.add("location = ?");
        values.add(project.getLocation());
     }
     if(updatePrice_min){
        setClauses.add("price_min = ?");
        values.add(project.getPriceMin());
     }
     if(updatePrice_max){
        setClauses.add("price_max = ?");
        values.add(project.getPriceMax());
     }
     if(updateStatus){
        setClauses.add("status = ?");
        values.add(project.getStatus());
     }
     
    setClauses.add("updated_at = ?");
    values.add(project.getUpdatedAt());
     

    if(setClauses.isEmpty()){
        System.out.println("更新対象が選択されていません");
        return;
    }

    String sql = "UPDATE projects SET " 
              + String.join(",", setClauses) 
              + " WHERE id = ?";
    values.add(project.getId());

    try(Connection conn = Database.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql)){

            for(int i = 0; i < values.size(); i++){
                stm.setObject(i + 1,values.get(i));
            }

            stm.executeUpdate();
            System.out.println("更新完了！");

        }catch(java.sql.SQLException e){
            e.printStackTrace();
            System.out.println("更新に失敗しました");
        }
    }

    public Project findById(int id) {
    String sql = "SELECT * FROM projects WHERE id = ?";
    
    try (Connection conn = Database.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, id);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                // データベースのレコードを Project オブジェクトに変換する
                return new Project(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("client_name"),
                    rs.getString("required_skill"),
                    rs.getString("location"),
                    rs.getInt("price_min"),
                    rs.getInt("price_max"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // 見つからない場合は null を返す
    return null;
}

}
