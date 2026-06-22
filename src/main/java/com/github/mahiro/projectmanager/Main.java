package com.github.mahiro.projectmanager;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in,"UTF-8");
        ProjectRepository repo = new ProjectRepository();

        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("データベース連携成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("データベースにつながっていません");
        }

        boolean running = true;
        System.out.println("案件配信ツールです。実行したい項目を番号で選んでください。");
        while (running) {
            System.out.println("1:案件一覧表示  2:案件詳細表示  3:案件登録  4:案件更新  5:案件削除");
            System.out.print("操作番号:");

            int number = scan.nextInt();
            scan.nextLine();

            switch (number) {

                // 案件一覧表示
                case 1:
                    System.out.println("==========案件一覧===========");
                    List<Project> list = repo.findAll();

                    for (Project p : list) {
                        System.out.println();
                        System.out.print(p.toString());
                        System.out.println();
                    }
                    System.out.println("============================");
                    System.out.println();
                    break;

                // 案件詳細表示
                case 2:
                    List<Project> lists = repo.findAll();

                    for (Project p : lists) {
                        System.out.println();
                        System.out.print(p.toString());
                        System.out.println();
                    }
                    System.out.println("詳細を表示したい案件番号を入力してください");
                    System.out.print("番号:");
                    int select_id = scan.nextInt();
                    scan.nextLine();
                    repo.showDetail(select_id);
                    System.out.println();
                    break;

                // 案件登録
                case 3:
                    System.out.println("タイトルを入力してください(必須)");
                    String title = scan.nextLine();
                    System.out.println(title);
                    System.out.println();

                    System.out.println("会社名を入力してください(必須)");
                    String clientName = scan.nextLine();
                    System.out.println();

                    System.out.println("必須スキルを入力してください(任意)");
                    String requiredSkills = scan.nextLine();
                    System.out.println();

                    System.out.println("勤務地を入力してください(任意)");
                    String location = scan.nextLine();
                    System.out.println();

                    System.out.println("最低金額を入力してください(任意)");
                    Integer priceMin = Integer.parseInt(scan.nextLine());
                    System.out.println();

                    System.out.println("最高金額を入力してください(任意)");
                    Integer priceMax = Integer.parseInt(scan.nextLine());
                    System.out.println();

                    Project newProject = new Project(null, title, clientName, requiredSkills, location, priceMin,
                            priceMax, null, null, null);

                    repo.insert(newProject);
                    System.out.println();
                    break;

                // 案件更新
                case 4:
                    System.out.println("更新処理を開始します...");

                    List<Project> projects = repo.findAll();

                    for (Project p : projects) {
                        System.out.println();
                        System.out.print(p.toString());
                        System.out.println();
                    }

                    System.out.print("更新したい番号を入力してください");
                    System.out.print("番号:");
                    int change_id = scan.nextInt();
                    scan.nextLine();
                    repo.showDetail(change_id);

                    boolean updateTitle = false;
                    boolean updateClientName = false;
                    boolean updateRequiredSkills = false;
                    boolean updateLocation = false;
                    boolean updatePriceMin = false;
                    boolean updatePriceMax = false;
                    boolean updateStatus = false;
                    

                    Project project = repo.findById(change_id);

                    if (project == null) {
                        System.out.println("指定されたIDは見つかりませんでした");
                        break;
                    }     
                        System.out.print("案件名を変更してください（変更しない場合はEnter) : ");
                        String newTitle = scan.nextLine();

                        if (!newTitle.isEmpty()) {
                            project.setTitle(newTitle);
                            updateTitle = true;
                        }

                    

                    System.out.print("会社名を変更してください（変更しない場合はEnter）： ");
                    String newClientName = scan.nextLine();

                    if (!newClientName.isEmpty()) {
                        project.setClientName(newClientName);
                        updateClientName = true;
                    }

                    System.out.print("必須スキルを変更してください（変更しない場合はEnter）： ");
                    String newRequiredSkills = scan.nextLine();

                    if (!newRequiredSkills.isEmpty()) {
                        project.setRequiredSkills(newRequiredSkills);
                        updateRequiredSkills = true;
                    }

                    System.out.print("勤務地を変更してください（変更しない場合はEnter）： ");
                    String newLocation = scan.nextLine();

                    if (!newLocation.isEmpty()) {
                        project.setLocation(newLocation);
                        updateLocation = true;
                    }

                    System.out.print("最低金額を変更してください（変更しない場合はEnter）： ");
                    String inputPriceMin = scan.nextLine();

                    if (!inputPriceMin.isEmpty()) {
                        Integer newPriceMin = Integer.parseInt(inputPriceMin);
                        project.setPriceMin(newPriceMin);
                        updatePriceMin = true;
                    }

                    System.out.print("最高金額を変更してください（変更しない場合はEnter）： ");
                    String inputPriceMax = scan.nextLine();

                    if (!inputPriceMax.isEmpty()) {
                        Integer newPriceMax = Integer.parseInt(inputPriceMax);
                        project.setPriceMax(newPriceMax);
                        updatePriceMax = true;
                    }

                    System.out.print("配属状況を変更してください（変更しない場合はEnter）： ");
                    String newStatus = scan.nextLine();

                    if (!newStatus.isEmpty()) {
                        project.setStatus(newStatus);
                        updateStatus = true;
                    }

                    project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    

                    repo.update(
                            project,
                            updateTitle,
                            updateClientName,
                            updateRequiredSkills,
                            updateLocation,
                            updatePriceMin,
                            updatePriceMax,
                            updateStatus
                        );
                        System.out.println();
                    break;

                case 5:
                    System.out.println("削除処理を開始します...");
            }

        }
        scan.close();
    }

}
