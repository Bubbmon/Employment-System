package com.system.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Legion
 * @Date 2021/6/12 18:42
 * @Description 招聘信息相关
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    /**
     * 获取招聘信息详情页
     * @param id 招聘信息id
     * @return
     */
    @GetMapping(path = "/{id}")
    public String getPosition(@Param("id") String id) {
        return null;
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
    @PostMapping(path = "/post")
    public String postPosition(@RequestHeader long token, @RequestHeader String position,
                               @RequestHeader String title, @RequestBody String content,
                               @RequestHeader String salary, @RequestHeader String degree) {
        return null;
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
    @PostMapping(path = "/modify/{positionId}")
    public String modifyPosition(@RequestHeader long token, @RequestHeader String position,
                                 @RequestHeader String title, @RequestBody String content,
                                 @RequestHeader String salary, @RequestHeader String degree,
                                 @Param("positionId") String positionId) {
        return null;
    }

    /**
     * 查看公司所有的招聘信息
     * @return
     */
    @GetMapping(path = "/enterprise/{enterpriseId}")
    public String getEnterprisePosition(@Param("enterpriseId") long enterpriseId) {
        return null;
    }


}
