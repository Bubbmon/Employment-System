package com.system.controller;

import com.system.interceptor.LogMe;
import com.system.interceptor.NeedVerify;
import com.system.service.TalkService;
import com.system.util.DecodeUtil;
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
@RequestMapping(path = "/talk", produces = "application/json;charset=utf-8")
@LogMe
@CrossOrigin("*")
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

    @GetMapping("/new")
    @NeedVerify
    public String getUnsentHistory(@RequestHeader String token) {
        return talkService.sendUnsentTalkToken(token);
    }


    @NeedVerify
    @PostMapping("/talkTo")
    public String talkTo(@RequestHeader String token,@RequestHeader String id, @RequestHeader String message) throws IOException {
        message = DecodeUtil.decode(message);
        talkService.talkToToken(token,id,message);
        return "{\"dealResult\":0}";
    }

    @NeedVerify
    @GetMapping("whoTalkedWithMe")
    public String getTalkerId(@RequestHeader String token){
        return talkService.getTalker(token);
    }

}
