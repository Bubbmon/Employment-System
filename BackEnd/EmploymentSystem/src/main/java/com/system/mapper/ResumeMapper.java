package com.system.mapper;

import com.system.entity.Resume;
import com.system.entity.ResumeId;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author jiach
 * @Date 2021/6/14 16:08
 * @Description
 */
@Mapper
@Component
public interface ResumeMapper{

    List<Resume> searchByUserId(String userId);

    List<Resume> searchByPositionId(long positionId);

    Resume searchByIds(ResumeId resumeId);

    int modifyDeal(Resume resume);
}
