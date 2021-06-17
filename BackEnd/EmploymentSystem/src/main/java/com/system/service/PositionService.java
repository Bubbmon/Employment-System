package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.Enterprise;
import com.system.entity.HumanResource;
import com.system.entity.PositionDetail;
import com.system.entity.PositionInfo;
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

    /**
     * 获取招聘信息详情页
     * @param id
     * @return
     */
    public String getPosition(long id){
        positionMapper.updateView(id);
        PositionInfo positionInfo = positionMapper.getPositionInfo(id);
        String hrId = positionInfo.getHrId();
        HumanResource hrInfo = humanResourceMapper.search(hrId);
        PositionDetail detail = new PositionDetail();
        detail.setId(id);
        detail.setPosition(positionInfo.getPosition());
        detail.setEnterpriseName(positionInfo.getEnterpriseName());
        detail.setTitle(positionInfo.getTitle());
        detail.setSalary(positionInfo.getSalary());
        detail.setDegree(positionInfo.getDegree());
        detail.setViews(positionInfo.getViews());
        detail.setHrId(hrId);
        detail.setHrName(hrInfo.getName());
        detail.setContent(positionInfo.getContent());
        return JSON.toJSONString(detail);
    }

    public Long postPosition(String token, String position,String title, String content,String salary, String degree){
        String id = tokenUtil.check(token);
        HumanResource hr = humanResourceMapper.search(id);
        PositionInfo positionInfo = new PositionInfo();
        positionInfo.setEnterpriseId(hr.getEnterpriseId());
        positionInfo.setEnterpriseName(hr.getEnterpriseName());
        positionInfo.setHrId(id);
        positionInfo.setPosition(position);
        positionInfo.setTitle(title);
        positionInfo.setContent(content);
        positionInfo.setSalary(salary);
        positionInfo.setDegree(degree.charAt(0));
        return positionMapper.postPosition(positionInfo);
    }

    /**
     * hr修改简历
     * @param token
     * @param position
     * @param title
     * @param content
     * @param salary
     * @param degree
     * @param positionId
     * @return
     */
    public String updatePosition(String token,String position,String title, String content,String salary, String degree,long positionId){
        String hrId = tokenUtil.check(token);
        int result = 0;
        PositionInfo positionInfo = positionMapper.getPositionInfo(positionId);
        System.out.println(positionInfo.getHrId());
        if(hrId.equals(positionInfo.getHrId())){
            positionInfo.setPosition(position);
            positionInfo.setTitle(title);
            positionInfo.setContent(content);
            positionInfo.setSalary(salary);
            positionInfo.setDegree(degree.charAt(0));
            int count = positionMapper.updatePosition(positionInfo);
            if(count == 0) {
                result = 1;
            }
        }else{
            result = 1;
        }
        return "{\"positionResult:\""+result+"}";
    }

    public String getEnterprisePosition(long enterpriseId){
        return JSON.toJSONString(positionMapper.findFromEnterprise(enterpriseId));
    }
}
