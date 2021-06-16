package com.system.service;

import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
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
    }
    public static ConcurrentHashMap<String,WebSocketSession> map;
    @Autowired
    TokenUtil util;

    public void put(String key, WebSocketSession session){
        if(key!=null) map.put(key,session);
    }
    public void remove(String key){
        if(key!=null) map.remove(key);
    }

    public void talkToToken(String token,String to,String message) throws IOException {
        talkTo(util.check(token),to,message);
    }
    public void talkTo(String from,String to,String message) throws IOException {
        WebSocketSession session = map.get(to);
        if(session!=null&&session.isOpen()){
            session.sendMessage(new TextMessage("{'id':'"+from+"','message':'"+message+"'}"));
        }else{
            ;// TODO: 放到缓冲区？
        }
    }
}
