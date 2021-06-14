package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Legion
 * @Date 2021/6/12 18:39
 * @Description 搜索相关
 */
@RestController
@CrossOrigin("*")
public class SearchController {
    /**
     * 搜索
     * @param keyword 关键词
     * @param position 岗位类型
     * @param degree 学历
     * @return
     */
    @GetMapping(path = "/search")
    public String search(@RequestParam("keyword")String keyword, @RequestParam("position") String position,
                         @RequestParam("degree") String degree) {
        return null;
    }

    /**
     * 推荐
     * @param token 用户标识
     * @return
     */
    @GetMapping(path = "/recommend")
    public String recommend(@RequestHeader String token) {
        return null;
    }

    /**
     * 获取资讯列表
     * @return
     */
    @GetMapping(path = "/info")
    public String infoList() {
        return null;
    }

    /**
     * 获取单个资讯
     * @param id 资讯id
     * @return
     */
    @GetMapping(path = "/info/{id}")
    public String info(@PathVariable("id") String id) {
        return null;
    }
}
