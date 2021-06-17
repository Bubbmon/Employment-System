package com.system.controller;

import com.system.interceptor.NeedVerify;
import com.system.service.TalkService;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author Legion
 * @Date 2021/6/12 19:56
 * @Description
 */
@RestController
@RequestMapping("/talk")
public class TalkController {
    @Autowired
    TalkService talkService;
    @Autowired
    TokenUtil util;

    /**
     * 查看历史聊天记录
     * @param token 用户标识
     * @param id 对方的账号id
     * @return
     */
    @GetMapping("/history")
    @NeedVerify
    public String talkHistory(@RequestHeader String token, @RequestHeader String id) {
        return talkService.getHistory(util.check(token),id);
    }


    @PostMapping("/talkTo")
    public String talkTo(@RequestHeader String token,@RequestHeader String id, @RequestHeader String message) throws IOException {
        talkService.talkToToken(token,id,message);
        return message;
    }
}
