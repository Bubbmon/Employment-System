package com.system.interceptor;

import com.system.service.TalkService;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-16 23:20
 **/
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired
    TalkService talkService;
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId=tokenUtil.getUserId(session);
        System.out.println("afterConnectionEstablished");
        if(userId!=null) talkService.put(userId,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = tokenUtil.getUserId(session);
        if(userId!=null) talkService.remove(userId);
    }


}
