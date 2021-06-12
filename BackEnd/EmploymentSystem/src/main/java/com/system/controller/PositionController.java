package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Legion
 * @Date 2021/6/12 18:42
 * @Description 招聘信息相关
 */
@RestController("position")
public class PositionController {
    @GetMapping(path = "/{id}")
    public String getPosition(@Param("id") String id) {
        return null;
    }
}
