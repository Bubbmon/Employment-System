package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.Enterprise;
import com.system.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseService{
    @Autowired
    EnterpriseMapper enterpriseMapper;

    public String getEnterpriseInfo(String id){
        Long idLong = Long.parseLong(id);
        Enterprise enterprise = enterpriseMapper.searchById(idLong);
        enterprise.setCode(null);
        enterprise.setPosition(null);
        return JSON.toJSONString(enterprise);
    }
}
