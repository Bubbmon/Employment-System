package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Legion
 * @Date 2021/6/12 18:38
 * @Description 企业相关
 */
@RestController
@RequestMapping("/enterprise")
@CrossOrigin("*")
public class EnterpriseController {
    /**
     * 查询企业信息
     * @param id 企业id
     */
    @GetMapping(path = "/info/{id}")
    public String getEnterpriseInfo(@PathVariable("id") String id) {
        return null;
    }
}
