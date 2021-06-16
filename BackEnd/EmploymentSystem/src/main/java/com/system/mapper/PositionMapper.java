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
    List<PositionInfo> recommend(String position, int count);
}
