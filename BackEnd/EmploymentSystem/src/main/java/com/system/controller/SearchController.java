package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Legion
 * @Date 2021/6/12 18:39
 * @Description 搜索相关
 */
@RestController
public class SearchController {
    @GetMapping(path = "/search")
    public String search(@RequestParam("keyword")String keyword, @RequestParam("position") String position,
                         @RequestParam("degree") String degree) {
        return null;
    }
}
