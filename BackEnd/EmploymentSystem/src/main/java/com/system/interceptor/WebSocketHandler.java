package com.system.interceptor;

import com.system.service.TalkService;
import com.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-16 23:20
 **/
@Component
@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired
    TalkService talkService;
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String userId=tokenUtil.getUserId(session);
        log.info("ws: user="+userId+" connectionEstablished.");
        if(userId!=null) {
            talkService.put(userId,session);
            talkService.sendHistory(userId,session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = tokenUtil.getUserId(session);
        log.info("ws: user="+userId+" connectionClosed.");
        if(userId!=null) talkService.remove(userId);
    }


}
