package com.system.entity;

import lombok.Data;

/**
 * HR查看这个职位都有哪些招聘者
 */
@Data
public class ReceiveResumeInfo{
    private String id;
    private String name;
    private String phone;
    private boolean isDealed;
}
