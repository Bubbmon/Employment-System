package com.system.entity;

import lombok.Data;

/**
 * @Author Legion
 * @Date 2021/6/12 15:20
 * @Description
 */
@Data
public class UserInfo implements Customer {
    private String id;
    private String pswd;
    private String name;
    private String IDNO;
    private String phone;
    private String email;
    private int age;
    private String interest;
}
