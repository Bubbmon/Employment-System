package com.system.controller;

import com.system.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Legion
 * @Date 2021/6/12 18:38
 * @Description 企业相关
 */
@Slf4j
@RestController
@RequestMapping(path = "/enterprise",produces = "application/json;charset=utf-8")
@CrossOrigin("*")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;
    /**
     * 查询企业信息
     * @param id 企业id
     */
    @GetMapping(path = "/info/{id}")
    public String getEnterpriseInfo(@PathVariable("id") String id){
        log.info("Get enterprise id:"+id);
        String enterpriseInfo = enterpriseService.getEnterpriseInfo(id);
        log.info("Get enterprise info:"+enterpriseInfo);
        return enterpriseInfo;
    }
}
