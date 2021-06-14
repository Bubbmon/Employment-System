package com.system.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.EmbeddedId;

/**
 * @Author Legion
 * @Date 2021/6/12 15:27
 * @Description
 */
@Data
public class Resume {
    @Id
    private long positionId;
    @Id
    private String userId;
    private String resume;
    private boolean isDealed;
}
