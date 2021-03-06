package com.system.controller;

import com.system.interceptor.NeedVerify;
import com.system.service.PositionService;
import com.system.util.DecodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Legion
 * @Date 2021/6/12 18:42
 * @Description 招聘信息相关
 */
@RestController
@RequestMapping(value = "/position", produces = "application/json;charset=utf-8")
@Slf4j
@CrossOrigin("*")
public class PositionController {
    @Autowired
    PositionService positionService;
    /**
     * 获取招聘信息详情页
     * @param id 招聘信息id
     * @return
     */
    @GetMapping(path = "/{id}")
    public String getPosition(@PathVariable("id") long id) {
        return positionService.getPosition(id);
    }

    /**
     * hr发布招聘信息
     * @param token 用户标识
     * @param position 岗位类型
     * @param title 标题
     * @param content 内容
     * @param salary 薪酬
     * @param degree 学历
     * @return
     */
    @NeedVerify
    @PostMapping(path = "/post")
    public String postPosition(@RequestHeader String token, @RequestHeader String position,
                               @RequestHeader String title, @RequestBody String content,
                               @RequestHeader String salary, @RequestHeader String degree) {
        title = DecodeUtil.decode(title);
        content = DecodeUtil.decode(content);
        long positionId = positionService.postPosition(token,position,title,content,salary,degree);
        return "{\"positionResult\":0,\"positionId\":"+ positionId+"}";
    }

    /**
     * hr修改招聘信息
     * @param token 用户标识
     * @param position 岗位类型
     * @param title 标题
     * @param content 内容
     * @param salary 薪酬
     * @param degree 学历
     * @param positionId 招聘信息id
     * @return
     */
    @NeedVerify
    @PostMapping(path = "/modify/{positionId}")
    public String modifyPosition(@RequestHeader String token, @RequestHeader String position,
                                 @RequestHeader String title, @RequestBody String content,
                                 @RequestHeader String salary, @RequestHeader String degree,
                                 @PathVariable("positionId") long positionId){
        title = DecodeUtil.decode(title);
        String result = positionService.updatePosition(token, position, title, content, salary, degree, positionId);
        return result;
    }

    /**
     * 非hr查看公司所有的招聘信息
     * @return
     */
    @GetMapping(path = "/enterprise/{enterpriseId}")
    public String getEnterprisePosition(@PathVariable("enterpriseId") long enterpriseId) {
        log.info("Receive getEnterprisePosition, enterpriseId="+enterpriseId);
        return positionService.getEnterprisePosition(enterpriseId);
    }

    /*hr查看公司所有的招聘信息*/
    @PostMapping(path = "/enterprise/{enterpriseId}")
    public String hrGetEnterprisePosition(@RequestHeader String token,@PathVariable("enterpriseId") long enterpriseId){
        log.info("Receive token:"+token+",enterpriseId:"+enterpriseId);
        String result = positionService.hrGetEnterprisePosition(token, enterpriseId);
        log.info("Get position infos:"+result);
        return result;
    }
}
