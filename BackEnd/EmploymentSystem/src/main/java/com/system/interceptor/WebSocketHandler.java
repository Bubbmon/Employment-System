package com.system.interceptor;

import com.system.service.TalkService;
import com.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

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

    @Value("${wsPrefix.open}")
    private String open;
    @Value("${code.logInExpiredCode}")
    private Integer logInExpiredCode;
    @Value("${code.logInExpiredMessage}")
    private String logInExpiredMessage;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("ws: some one establish a connection.");
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        if(msg.startsWith(open)){
            String userId=tokenUtil.check(msg.replace(open,""));
            if(userId!=null) {
                talkService.put(session,userId);
                talkService.put(userId,session);
            }else{
                session.sendMessage(new TextMessage("{\"error_code\":"+logInExpiredCode+", \"error_msg\":\""+logInExpiredMessage+"\"}"));
            }
            log.info("ws: user="+userId+" come int.");
        }else{
            session.sendMessage(new TextMessage("your prefix in ["+msg+"]is wrong!"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = talkService.getUserId(session);
        talkService.remove(session);
        talkService.remove(userId);
        log.info("ws: some one close a connection.");
    }


}
