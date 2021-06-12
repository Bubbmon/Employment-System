package com.system.entity;

import lombok.Data;

/**
 * @Author Legion
 * @Date 2021/6/12 15:24
 * @Description
 */
@Data
public class HumanResource implements Customer {
    private String id;
    private String pswd;
    private String name;
    private long enterpriseId;
    private String department;
    private String phone;
    private String email;
    private String enterpriseName;
    private String code;
}
