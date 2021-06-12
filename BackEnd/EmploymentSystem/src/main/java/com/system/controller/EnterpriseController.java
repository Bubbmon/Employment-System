package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Legion
 * @Date 2021/6/12 18:38
 * @Description 企业相关
 */
@RestController("/enterprise")
public class EnterpriseController {
    @GetMapping(path = "/info/{id}")
    public String getEnterpriseInfo(@Param("id") String id) {
        return null;
    }
}
