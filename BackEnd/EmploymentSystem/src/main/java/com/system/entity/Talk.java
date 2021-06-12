package com.system.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author Legion
 * @Date 2021/6/12 15:28
 * @Description
 */
@Data
public class Talk {
    private String userId;
    private String hrId;
    private Timestamp time;
    private char sender;
    private String message;
}
