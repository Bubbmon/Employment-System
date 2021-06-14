package com.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Legion
 * @Date 2021/6/12 19:56
 * @Description
 */
@RestController
@RequestMapping("/talk")
public class TalkController {
    /**
     * 查看历史聊天记录
     * @param token 用户标识
     * @param id 对方的账号id
     * @return
     */
    @GetMapping("/history")
    public String talkHistory(@RequestHeader String token, @RequestHeader String id) {
        return null;
    }

    //TODO: websocket构建session
}
