package com.system.mapper;

import com.system.entity.Info;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Legion
 * @Date 2021/6/16 23:50
 * @Description
 */
@Mapper
@Component
public interface InfoMapper {
    List<Info> searchAll();
    Info searchById(Long id);
}
