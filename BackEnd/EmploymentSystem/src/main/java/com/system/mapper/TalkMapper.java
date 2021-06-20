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
    List<Talk> getTalk(String from,String to);
    void insertHistoryTalks(List<Talk> talks);
    void insertHistoryTalk(Talk talk);
    void insertUnsentTalk(Talk talk);
    List<Talk> selectUnsentTalk(String to);
    void deleteUnsentTalk(String to);
    List<Talk> getTalker(String id);
}
