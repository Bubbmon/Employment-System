package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Legion
 * @Date 2021/6/12 18:38
 * @Description 企业相关
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    /**
     * 查询企业信息
     * @param id 企业id
     */
    @GetMapping(path = "/info/{id}")
    public String getEnterpriseInfo(@Param("id") String id) {
        return null;
    }
}
