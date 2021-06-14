package com.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
public class ResumeId{
    private long positionId;
    private String userId;
}
