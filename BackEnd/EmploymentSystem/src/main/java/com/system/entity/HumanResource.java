package com.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Legion
 * @Date 2021/6/12 15:24
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
