package com.system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
<<<<<<< HEAD
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
=======
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
>>>>>>> 9171a888de71fdd55113f576d21f17ad9eafc481

/**
 * @Author Legion
 * @Date 2021/6/14 13:59
 * @Description
 */
@Component
@Slf4j
public class TokenUtil {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value("${spring.redis.tokenTime}")
    private String seconds;

    /**
     * 根据时间造一个token，加个锁免得大家都来
     * @param isRecruiter 是否为招聘者
     * @return token:h/u+账号id+时间戳（其中h代表hr，u代表user）
     */
    public synchronized String generateToken(boolean isRecruiter, String id) {
        long time = System.currentTimeMillis();
        if (isRecruiter) {
            return "u"+id+time;
        } else {
            return "h"+id+time;
        }
    }
    public String getUserId(WebSocketSession session){
        List<String> ls =session.getHandshakeHeaders().get("token");
        if(ls==null||ls.size()==0) return null;
        else return check(ls.get(0));
    }

    /**
     * 保存token
     * @param token token
     * @param id 账户id
     */
    public void save(String token, String id) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return;
        }
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(token, id, Integer.parseInt(seconds));
    }

    /**
     * 检查token（通用）
     * @param token token
     * @return 账户id，如果没有则返回null
     */
    public String check(String token) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return null;
        }
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(token);
    }

    /**
     * 判断该token对应用户的身份是否为求职者
     * @param token
     * @return
     * @throws Exception
     */
    public boolean isRecruiter(String token)  {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return true;
        }
        char first = token.charAt(0);
        if (first=='h') return false;
        return true;
    }


}
