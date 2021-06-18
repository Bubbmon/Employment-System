package com.system.mapper;

import com.system.entity.PositionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface PositionMapper{
    PositionInfo getPositionInfo(@Param("positionId")long positionId);
    List<PositionInfo> search(String keyword, String position, Character degree);
    List<PositionInfo> findFromEnterprise(long enterpriseId);
    List<PositionInfo> recommend(String position, int count);
    int updatePosition(PositionInfo positionInfo);
    Long postPosition(PositionInfo positionInfo);
    void updateView(@Param("id") long positionId);
}
