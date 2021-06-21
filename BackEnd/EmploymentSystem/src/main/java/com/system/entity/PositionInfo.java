package com.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @Author Legion
 * @Date 2021/6/12 15:26
 * @Description
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PositionInfo {
    private long id;
    @NonNull
    private String position;
    @NonNull
    private String enterpriseName;
    @NonNull
    private long enterpriseId;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String salary;
    @NonNull
    private String hrId;
    @NonNull
    private char degree;
    private int views;
    private int hasPrivilege;
}
