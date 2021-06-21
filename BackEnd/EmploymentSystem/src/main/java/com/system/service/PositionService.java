package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.Enterprise;
import com.system.entity.HumanResource;
import com.system.entity.PositionDetail;
import com.system.entity.PositionInfo;
import com.system.mapper.EnterpriseMapper;
import com.system.mapper.HumanResourceMapper;
import com.system.mapper.PositionMapper;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-17 01:47
 **/
@Service
public class PositionService {
    @Autowired
    EnterpriseMapper enterpriseMapper;
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
        PositionInfo positionInfo = positionMapper.getPositionInfo(id);
        if(positionInfo==null) return "";
        String hrId = positionInfo.getHrId();
        if(hrId==null) return "";
        positionMapper.updateView(id);
        HumanResource hrInfo = humanResourceMapper.search(hrId);
        long enterpriseId = positionInfo.getEnterpriseId();
        Enterprise enterprise = enterpriseMapper.searchById(enterpriseId);
        PositionDetail detail = new PositionDetail();
        detail.setId(id);
        detail.setEnterpriseId(enterpriseId);
        detail.setPosition(positionInfo.getPosition());
        detail.setEnterpriseName(enterprise.getName());
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
        positionInfo.setHrId(id);
        positionInfo.setPosition(position);
        positionInfo.setTitle(title);
        positionInfo.setContent(content);
        positionInfo.setSalary(salary);
        positionInfo.setDegree(degree.charAt(0));
        positionMapper.postPosition(positionInfo);
        return positionInfo.getId();
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
            long count = positionMapper.updatePosition(positionInfo);
            if(count == 0) {
                result = 1;
            }
        }else{
            result = 1;
        }
        return "{\"positionResult:\""+result+"}";
    }


    /**
     * 非hr查看公司的所有招聘职位
     * @param enterpriseId
     * @return
     */
    public String getEnterprisePosition(long enterpriseId){
        return JSON.toJSONString(positionMapper.findFromEnterprise(enterpriseId));
    }

    /**
     * hr查看公司的所有招聘职位
     * @param token
     * @param enterpriseId
     * @return
     */
    public String hrGetEnterprisePosition(String token,long enterpriseId){
        List<PositionInfo> positionInfos = positionMapper.hrFindPositions(enterpriseId);
        List<PositionInfo> resultList = new ArrayList<>();
        String hrId = tokenUtil.check(token);
        for(PositionInfo positionInfo : positionInfos){
            String phrId = positionInfo.getHrId();
            int hasPrivilege = (hrId.equals(phrId)) ? 1 : 0;
            positionInfo.setHasPrivilege(hasPrivilege);
            resultList.add(positionInfo);
        }
        return JSON.toJSONString(resultList);
    }
}
