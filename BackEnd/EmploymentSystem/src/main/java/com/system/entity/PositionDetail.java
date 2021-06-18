package com.system.entity;

import lombok.Data;

@Data
public class PositionDetail{
    private long id;
    private String position;
    private long enterpriseId;
    private String enterpriseName;
    private String title;
    private String salary;
    private char degree;
    private int views;
    private String hrId;
    private String hrName;
    private String content;
}
