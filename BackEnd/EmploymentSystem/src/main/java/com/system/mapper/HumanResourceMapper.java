package com.system.mapper;

import com.system.entity.HumanResource;
import com.system.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author Legion
 * @Date 2021/6/12 16:09
 * @Description
 */
@Mapper
@Component
public interface HumanResourceMapper {
    HumanResource search(@Param("id") String id);

    int insert(HumanResource humanResource);

    int update(HumanResource humanResource);
}
