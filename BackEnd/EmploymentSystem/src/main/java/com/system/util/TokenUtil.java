package com.system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

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
    @Value("redis.tokenTime")
    private int seconds;

    /**
     * 根据时间造一个token，加个锁免得大家都来
     * @param isRecruiter 是否为招聘者
     * @return token:h/u+时间戳（其中h代表hr，u代表user）
     */
    public synchronized String generateToken(boolean isRecruiter, String id) {
        long time = System.currentTimeMillis();
        if (isRecruiter) {
            return "u"+time;
        } else {
            return "h"+time;
        }
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
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        if (token.charAt(0)=='h') {
            hashOps.put("hr", token, id);
        }
        if (token.charAt(0)=='u') {
            hashOps.put("user", token, id);
        }
    }

    /**
     * 检查token
     * @param token token
     * @return 账户id，如果没有则返回null
     */
    public String check(String token) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return null;
        }
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String id = null;
        if(token.charAt(0)=='h') {
            id = hashOps.get("hr", token);
        }else if (token.charAt(0)=='u') {
            id = hashOps.get("user", token);
        }
        return id;
    }


}
