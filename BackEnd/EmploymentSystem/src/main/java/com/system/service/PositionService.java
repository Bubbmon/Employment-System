package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.HumanResource;
import com.system.mapper.HumanResourceMapper;
import com.system.mapper.PositionMapper;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-17 01:47
 **/
@Service
public class PositionService {
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    HumanResourceMapper humanResourceMapper;
    @Autowired
    TokenUtil tokenUtil;

    public String getPosition(long id){
        return JSON.toJSONString(positionMapper.getPositionInfo(id));
    }

    public Long postPosition( String token, String position,String title, String content,String salary, String degree){
        String id = tokenUtil.check(token);
        HumanResource hr = humanResourceMapper.search(id);
        return positionMapper.postPosition(hr.getEnterpriseName(),hr.getEnterpriseId(),id,position,title,content,salary,degree);
    }

    public void updatePosition(String token,String position,String title, String content,String salary, String degree,String positionId){
        String hrId = tokenUtil.check(token);
        positionMapper.updatePosition(hrId,position,title,content,salary,degree,positionId);
    }
    public String getEnterprisePosition(long enterpriseId){
        return JSON.toJSONString(positionMapper.findFromEnterprise(enterpriseId));
    }
}
