package com.system.entity;

import lombok.Data;

/**
 * @Author Legion
 * @Date 2021/6/12 15:26
 * @Description
 */
@Data
public class PositionInfo {
    private long id;
    private String position;
    private long enterpriseId;
    private String title;
    private String content;
    private String salary;
    private String hrId;
    private char degree;
    private int views;
}
