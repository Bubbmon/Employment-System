package com.system.mapper;

import com.system.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author Legion
 * @Date 2021/6/12 15:33
 * @Description
 */
@Mapper
@Component
public interface UserInfoMapper {
    UserInfo search(@Param("id") String id);

    int insert(UserInfo userInfo);

    int update(UserInfo userInfo);

    String searchResume(String id);

    int updateResume(UserInfo userInfo);
}
