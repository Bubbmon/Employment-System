package com.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author Legion
 * @Date 2021/6/12 15:28
 * @Description
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Talk {
    @NonNull
    private String from;
    @NonNull
    private String to;
    private Timestamp time; // 插入的时候直接mysql生成好了
    @NonNull
    private String message;
}
