package com.system.controller;

import com.alibaba.fastjson.JSON;
import com.system.entity.Enterprise;
import com.system.entity.Info;
import com.system.entity.PositionInfo;
import com.system.entity.UserInfo;
import com.system.mapper.EnterpriseMapper;
import com.system.mapper.InfoMapper;
import com.system.mapper.PositionMapper;
import com.system.mapper.UserInfoMapper;
import com.system.util.DecodeUtil;
import com.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2021/6/12 18:39
 * @Description 搜索相关
 */
@RestController
@RequestMapping(produces = "application/json;charset=utf-8")
@CrossOrigin("*")
@Slf4j
public class SearchController {
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    TokenUtil tokenUtil;
    /**
     * 搜索
     * @param keyword 关键词
     * @param position 岗位类型
     * @param degreeStr 学历
     * @param pageStr 页码
     * @param pageSizeStr 单页大小
     * @return
     */
    @GetMapping(path = "/search")
    public String search(@RequestParam("keyword")String keyword, @RequestParam("position") String position,
                         @RequestParam("degree") String degreeStr, @RequestParam("page") String pageStr,
                         @RequestParam("pageSize") String pageSizeStr) {
        keyword = DecodeUtil.decode(keyword);
        log.info("Receive search: keyword="+keyword+", position="+position+", degree="+degreeStr+", page="+pageStr+", pageSize="+pageSizeStr);
        if(keyword==null || keyword.length()==0) keyword = null;
        if(position==null || position.length()==0) position = null;
        int page = 1;
        if (pageStr!=null && pageStr.length()!=0) page = Integer.parseInt(pageStr);
        if (page<=0) page = 1;
        int pageSize = 10;
        if (pageSizeStr!=null && pageSizeStr.length()!=0) pageSize = Integer.parseInt(pageSizeStr);
        if (pageSize<=0) pageSize = 10;
        Character degree = null;
        if (degreeStr!=null && degreeStr.length()!=0) degree = degreeStr.charAt(0);
        List<PositionInfo> list = positionMapper.search(keyword, position, degree);
        int size = list.size();
        if (page*pageSize<=size) {
            list = list.subList((page-1)*pageSize, page*pageSize);
        } else if ((page-1)*pageSize<size) {
            list = list.subList((page-1)*pageSize,size);
        } else if (pageSize<=size){
            list = list.subList(0,pageSize);
        }

        for (PositionInfo positionInfo : list) {
            Enterprise enterprise = enterpriseMapper.searchById(positionInfo.getEnterpriseId());
            positionInfo.setEnterpriseName(enterprise.getName());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("num",size);
        map.put("info",list);
        String result = JSON.toJSONString(map);
        log.info("Result: " + result);
        return result;
    }

    /**
     * 推荐
     * @param token 用户标识
     * @return
     */
    @GetMapping(path = "/recommend")
    public String recommend(@RequestHeader String token,@RequestHeader int count) {
        log.info("Receive recommend: token="+token+", count="+count);
        String id = tokenUtil.check(token);
        String interest = null;
        if (id!=null) {
            UserInfo userInfo = userInfoMapper.search(id);
            interest = userInfo.getInterest();
        }
        List<PositionInfo> list = positionMapper.recommend(interest, count);
        String result = JSON.toJSONString(list);
        log.info("Result: "+result);
        return result;
    }

    /**
     * 获取资讯列表
     * @return
     */
    @GetMapping(path = "/info")
    public String infoList() {
        log.info("Receive infoList");
        List<Info> list = infoMapper.searchAll();
        String result = JSON.toJSONString(list);
        log.info("Result: "+result);
        return result;
    }

    /**
     * 获取单个资讯
     * @param id 资讯id
     * @return
     */
    @GetMapping(path = "/info/{id}")
    public String info(@PathVariable("id") long id) {
        log.info("Receive info: id="+id);
        Info info = infoMapper.searchById(id);
        String result = JSON.toJSONString(info);
        log.info("Result: "+result);
        return result;
    }
}
