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
    List<Talk> getTalkBetween(String id1, String id2);
    void insertHistoryTalks(@Param("talks") List<Talk> talks);
    void insertHistoryTalk(Talk talk);
    void insertUnsentTalk(Talk talk);
    List<Talk> selectUnsentTalk(String to,String from);
    void deleteUnsentTalk(String to);
    List<Talk> getTalker(String id);
    List<Integer> selectUnsentId(String to);
}
