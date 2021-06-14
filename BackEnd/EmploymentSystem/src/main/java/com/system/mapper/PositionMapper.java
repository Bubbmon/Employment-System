package com.system.mapper;

import com.system.entity.PositionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface PositionMapper{
    PositionInfo getPositionInfo(long positionId);
}
