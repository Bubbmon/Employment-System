package com.system.entity;

import lombok.Data;

/**
 * @Author Legion
 * @Date 2021/6/12 15:23
 * @Description
 */
@Data
public class Enterprise {
    private long id;
    private String name;
    private String code;
    private String introduction;
    private String position;
    private String address;
}
