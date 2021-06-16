package com.system.mapper;

import com.system.entity.PositionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface PositionMapper{
    PositionInfo getPositionInfo(long positionId);
    List<PositionInfo> search(String keyword, String position, String degree);
    List<PositionInfo> findFromEnterprise(long enterpriseId);
    List<PositionInfo> recommend(String position, int count);
    void updatePosition(String hrId, String position,String title, String content,
                        String salary, String degree,String positionId);
    Long postPosition(String enterpriseName,long enterpriseId,String hrId,
                      String position,String title, String content,String salary,
                      String degree);

}
