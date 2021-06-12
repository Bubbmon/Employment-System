package com.system.entity;

import lombok.Data;

/**
 * @Author Legion
 * @Date 2021/6/12 15:27
 * @Description
 */
@Data
public class Resume {
    private long positionId;
    private String userId;
    private String resume;
    private boolean isDealed;
}
