package com.system.mapper;

import com.system.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author Legion
 * @Date 2021/6/12 16:12
 * @Description
 */
@Mapper
@Component
public interface EnterpriseMapper {

    Enterprise searchByName(@Param("name") String name);

    Enterprise searchById(@Param("id") long id);
}
