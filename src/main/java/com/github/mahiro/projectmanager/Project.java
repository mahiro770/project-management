package com.github.mahiro.projectmanager;

import java.sql.Timestamp;

//プライベートフィールド(カプセル化)
public class Project {
    private Integer id;
    private String title;
    private String clientName;
    private String requiredSkills;
    private String location;
    private Integer priceMin;
    private Integer priceMax;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;


//コンストラクタ
public  Project(Integer id,String title, String clientName, String requiredSkills, String location, Integer priceMin, Integer priceMax, String status,Timestamp createdAt,Timestamp updatedAt){
    this.id = id;
    this.title = title;
    this.clientName = clientName;
    this.requiredSkills = requiredSkills;
    this.location = location;
    this.priceMin = priceMin;
    this.priceMax = priceMax;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
}

//ゲッター
public Integer getId(){return this.id;}
public String getTitle(){return this.title;}
public String getClientName(){return this.clientName;}
public String getRequiredSkills(){return this.requiredSkills;}
public String getLocation(){return this.location;}
public Integer getPriceMin(){return this.priceMin;}
public Integer getPriceMax(){return this.priceMax;}
public String getStatus(){return this.status;}
public Timestamp getCreatedAt(){return this.createdAt;}
public Timestamp getUpdatedAt(){return this.updatedAt;}

public void setTitle(String title){this.title = title;}
public void setClientName(String clientName){this.clientName = clientName;}
public void setRequiredSkills(String requiredSkills){this.requiredSkills = requiredSkills;}
public void setLocation(String location){this.location = location;}
public void setPriceMin(Integer priceMin){this.priceMin = priceMin;}
public void setPriceMax(Integer priceMax){this.priceMax = priceMax;}
public void setStatus(String status){this.status = status;}
public void setUpdatedAt(Timestamp updatedAt){this.updatedAt = updatedAt;}


@Override
public String toString(){
    return 
    "Id:" + this.id +" | "+
    "案件名:" + this.title +" | "+
    "会社名:" + this.clientName +" | "+
    "必須スキル:" + this.requiredSkills +" | "+
    "勤務地:" + this.location +" | "+
    "最低金額ー最高金額:" + this.priceMin + " - " + this.priceMax +" | "+
    "配属状況:" + this.status +" | "+
    "取得日時:" + this.createdAt +" | "+
    "更新日時:" + this.updatedAt;    
}

}


