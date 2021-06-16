package com.system.mapper;

import com.system.entity.Talk;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-17 01:04
 **/
@Mapper
@Component
public interface TalkMapper {
    @Select("select * from talk where userId=#{user} and hrId=#{hr}")
    List<Talk> getTalk(@Param("user")String user,@Param("hr")String hr);


    @Options(useGeneratedKeys = true)
    @Insert("insert into talk(userId,hrId,time,message) values(#{userId},#{hrId},#{time},#{message})")
    boolean insertTalk(Talk talk);
}
