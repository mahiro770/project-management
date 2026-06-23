package com.github.mahiro.projectmanager;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in, "UTF-8");
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

        while (running) {
            
            System.out.println("案件配信ツールです。このまま操作を続けますか？（Y/N)");
            System.out.print("(Y/N):");
            String judgeString = scan.nextLine();

            if (judgeString.equals("Y") || judgeString.equals("y")) {
                
            } else if (judgeString.equals("N") || judgeString.equals("n")) {
                return;
            } else {
                System.out.println("不正な入力です。再度入力してください");
                continue;
            }
        

            System.out.println("操作を以下より選んでください(指定の番号えらんでください)");
            System.out.println("\n==========操作方法============");
            System.out.println("1:案件一覧表示");
            System.out.println("2:案件詳細表示");
            System.out.println("3:案件登録");
            System.out.println("4:案件更新");
            System.out.println("5:案件削除");
            System.out.println("\n==============================");
            System.out.print("操作番号:");

            int number = scan.nextInt();
            scan.nextLine();

            switch (number) {

                // 案件一覧表示
                case 1:
                    boolean IsValidId = false;
                    while (!IsValidId) {
                        System.out.print("一覧表示に進みますか(Y/N)？:");
                        String st = scan.nextLine();
                        if (st.equals("Y") || st.equals("y")) {
                            continue;
                        } else if (st.equals("N") || st.equals("n")) {
                            System.out.println("一覧表示をキャンセルします");
                            break;
                        }

                        System.out.println();
                        System.out.println("\n==========案件一覧===========");
                        List<Project> list = repo.findAll();

                        for (Project p : list) {
                            System.out.println();
                            System.out.print(p.toString());
                            System.out.println();
                        }
                        System.out.println("\n============================");
                        System.out.println();
                    }
                    break;

                // 案件詳細表示
                case 2:
                    boolean ValidId = false;
                    while (!ValidId) {
                        List<Project> lists = repo.findAll();

                        for (Project p : lists) {
                            System.out.println();
                            System.out.print(p.toString());
                            System.out.println();
                        }
                        System.out.println("詳細一覧表示を開始します（戻る場合は-1)");
                        System.out.println("詳細を表示したい案件番号を入力してください");
                        System.out.print("番号:");
                        int select_id = scan.nextInt();
                        scan.nextLine();

                        if (select_id == -1) {
                            System.out.println();
                            System.out.println("詳細表示をキャンセルします");
                            System.out.println();
                            break;
                        }
                        repo.showDetail(select_id);
                        System.out.println();
                    }
                    break;

                // 案件登録
                case 3:
                    System.out.println("案件登録を開始します");
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
                    boolean isValidId = false;
                    while (!isValidId) {

                        System.out.println("更新処理を開始します");

                        List<Project> projects = repo.findAll();

                        for (Project p : projects) {
                            System.out.println();
                            System.out.print(p.toString());
                            System.out.println();
                        }

                        System.out.print("更新したい番号を入力してください(戻る場合は、-1を入力)");
                        System.out.print("番号:");
                        int change_id = scan.nextInt();
                        scan.nextLine();
                        repo.showDetail(change_id);

                        if (change_id == -1) {
                            System.out.println();
                            System.out.println("更新処理をキャンセルしました");
                            System.out.println();
                            break;
                        }

                        boolean updateTitle = false;
                        boolean updateClientName = false;
                        boolean updateRequiredSkills = false;
                        boolean updateLocation = false;
                        boolean updatePriceMin = false;
                        boolean updatePriceMax = false;
                        boolean updateStatus = false;

                        System.out.println("\n==========案件一覧===========");
                        Project project = repo.findById(change_id);
                        System.out.println("\n============================");
                        System.out.println();

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
                                updateStatus);
                        System.out.println();
                    }
                    break;

                case 5:
                    boolean val = false;
                    while (!val) {

                        System.out.println("削除処理を開始します...");
                        System.out.println("削除する案件番号を入力してください(戻る場合は、-1)");
                        System.out.print("番号:");
                        int delete_id = scan.nextInt();
                        scan.nextLine();

                        if (delete_id == -1) {
                            System.out.println();
                            System.out.println("削除処理をキャンセルします");
                            System.out.println();
                            break;
                        }
                        System.out.println("本当に案件" + delete_id + "を削除してもいいですか？（Y/N)");
                        String judge = scan.nextLine();

                        if ("Y".equals(judge) || "y".equals(judge)) {
                            if (repo.delete(delete_id)) {
                                System.out.println("削除が完了しました");
                            } else {
                                System.out.println("指定された案件は見つかりませんでした");
                                System.out.println("1案件一覧 か 2案件詳細表示で確認してください");
                            }
                        } else if ("N".equals(judge) || "n".equals(judge)) {
                            System.out.println("削除をキャンセルしました");
                        } else {
                            System.out.println("無効な入力です。再度入力してください");
                        }

                    }
                    break;
                    
                default: System.out.println("不正な入力です。再度にゅうりょくしてください");
                 break;

            }

        }
        scan.close();
    }

}
