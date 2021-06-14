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
     * 检查token（通用）
     * @param token token
     * @return 账户id，如果没有则返回null
     */
    public String check(String token) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return null;
        }
        if(token.charAt(0)=='h') {
            return checkHr(token);
        }else if (token.charAt(0)=='u') {
            return checkUser(token);
        }
        return null;
    }

    /**
     * 检查token（hr）
     * @param token token
     * @return 账户id，如果没有则返回null
     */
    public String checkHr(String token) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return null;
        }
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.get("hr", token);
    }

    /**
     * 检查token（招聘者）
     * @param token token
     * @return 账户id，如果没有则返回null
     */
    public String checkUser(String token) {
        if (token==null || token.length()==0) {
            log.warn("The token is illegal");
            return null;
        }
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.get("user", token);
    }


}
