package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.Talk;
import com.system.mapper.TalkMapper;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-16 23:46
 **/
@Component
public class TalkService {
    static {
        map=new ConcurrentHashMap<>();
        sessionKey = new ConcurrentHashMap<>();
    }
    public static ConcurrentHashMap<String,WebSocketSession> map;
    public static ConcurrentHashMap<WebSocketSession,String> sessionKey;
    @Autowired
    TokenUtil util;
    @Autowired
    TalkMapper talkMapper;

    public void put(String key, WebSocketSession session){
        if(key!=null) map.put(key,session);
    }
    public void put(WebSocketSession session,String value){
        if(sessionKey!=null) sessionKey.put(session,value);
    }
    public void remove(String key){
        if(key!=null) map.remove(key);
    }
    public void remove(WebSocketSession session){
        if(sessionKey!=null) sessionKey.remove(session);
    }
    public String getUserId(WebSocketSession session){
        return sessionKey.get(session);
    }
    public void talkToToken(String token,String to,String message) throws IOException {
        talkTo(util.check(token),to,message);
    }

    public String getHistory(String from,String to){
        List<Talk> one = talkMapper.getTalk(from,to),
                   two = talkMapper.getTalk(to,from);
        one.addAll(two);
        return JSON.toJSONString(one);
    }

    public void talkTo(String from,String to,String message) throws IOException {
        WebSocketSession session = map.get(to);
        if(session!=null&&session.isOpen()){
            session.sendMessage(new TextMessage("{\"id\":\""+from+"\",\"message\":\""+message+"\"}"));
            talkMapper.insertHistoryTalk(new Talk(from,to,message));
        }else{
            talkMapper.insertUnsentTalk(new Talk(from,to,message));
        }
    }


    public String sendUnsentIdToken(String token){
        String userId = util.check(token);
        if(userId!=null) return sendUnsentIdId(userId);
        else return "{}";
    }

    public String sendUnsentIdId(String to){
        List<String> ids = new LinkedList<>();
        for(Talk talk:talkMapper.selectUnsentId(to)){
            ids.add(talk.getFrom());
        }
        return JSON.toJSONString(ids);
    }


    public String sendUnsentTalkToken(String token,String from) {
        String userId = util.check(token);
        if(userId!=null) return sendUnsentTalk(userId,from);
        else return "{}";
    }

    public String sendUnsentTalk(String to,String from) {
        List<Talk> talks = talkMapper.selectUnsentTalk(to,from);
        talkMapper.deleteUnsentTalk(to);
        talkMapper.insertHistoryTalks(talks);
        return JSON.toJSONString(talks);

    }

    public String getTalker(String token){
        String userId = util.check(token);
        Set<String> set = new HashSet<>();
        if(userId==null) return "{}";
        List<Talk> talks = talkMapper.getTalker(userId);
        for(Talk talk:talks){
            set.add(talk.getFrom());
            set.add(talk.getTo());
        }
        return JSON.toJSONString(set);
    }
}
