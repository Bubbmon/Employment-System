package com.system.entity;

import lombok.Data;


/**
 * 招聘者查看向哪些职位发了简历
 */
@Data
public class SendResumeInfo{
    private long positionId;
    private String positionTitle;
    private String enterpriseName;
    private boolean isDealed;
}
