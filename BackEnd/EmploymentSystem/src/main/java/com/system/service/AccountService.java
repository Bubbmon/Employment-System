package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.Customer;
import com.system.entity.Enterprise;
import com.system.entity.HumanResource;
import com.system.entity.UserInfo;
import com.system.mapper.EnterpriseMapper;
import com.system.mapper.HumanResourceMapper;
import com.system.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Legion
 * @Date 2021/6/12 16:38
 * @Description 账户相关的业务：注册，登陆，修改资料
 */
@Service
public class AccountService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    HumanResourceMapper humanResourceMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /**
     * 注册入口
     * @param customer 求职者实例或hr实例
     * @return json串，包括signUpResult(0:注册成功，1:账户已存在，2:插入失败) 和token(long型)
     */
    public String signUp(Customer customer) {
        int signUpResult = -1;
        long token = -1;
        if (customer instanceof UserInfo) {
            signUpResult = userSignUp((UserInfo) customer);
            token = getToken();
            //TODO: 将token放入redis

        }
        if(customer instanceof HumanResource) {
            signUpResult = hrSignUp((HumanResource) customer);
            token = getToken();
            //TODO: 将token放入redis

        }
        return "{\"signUpResult\":"+signUpResult+",\"token\":"+token+"}";
    }

    /**
     * 求职者注册，加个锁防止两个人同时注册一个账号
     * @param userInfo
     * @return 0:注册成功，1:账户已存在，2:插入失败
     */
    private synchronized int userSignUp(UserInfo userInfo) {
        UserInfo old = userInfoMapper.search(userInfo.getId());
        if (old==null) {
            try {
                int insert = userInfoMapper.insert(userInfo);
                return insert==0? 2:0;
            }catch (Exception e) {
                return 2;
            }
        } else {
            return 1;
        }
    }

    /**
     * hr注册，加个锁防止两个人同时注册一个账号
     * @param humanResource
     * @return 0:注册成功，1:账户已存在，2:插入失败
     */
    private synchronized int hrSignUp(HumanResource humanResource) {
        HumanResource old = humanResourceMapper.search(humanResource.getId());
        if(old!=null) return 1;
        try{
            Enterprise enterprise = enterpriseMapper.searchByName(humanResource.getEnterpriseName());
            if(enterprise==null) return 2;
            if(!enterprise.getCode().equals(humanResource.getCode())) return 2;
            int insert = humanResourceMapper.insert(humanResource);
            return insert==0? 2:0;
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * 登录入口
     * @param id 账号
     * @param pswd 密码
     * @param isRecruiter 是否为求职者
     * @return json串，包括signInResult(0:成功，1:失败)和token(long型)
     */
    public String signIn(String id, String pswd, boolean isRecruiter) {
        Customer customer = null;
        if (isRecruiter) {
            customer = userInfoMapper.search(id);
        } else {
            customer = humanResourceMapper.search(id);
        }
        if(customer.getPswd().equals(pswd)) {
            long token = getToken();
            //TODO: 将token放入redis

            return "{\"signInResult\":0,\"token\":"+token+"}";
        } else {
            return "{\"signInResult\":1}";
        }
    }

    /**
     * 修改资料
     * @param customer 求职者或hr的实例
     * @return json串，包括modifyResult(0:成功，1:失败)
     */
    public String modify(Customer customer) {
        int modifyResult = -1;
        int update = -1;
        if (customer instanceof UserInfo) {
            update = userInfoMapper.update((UserInfo) customer);
        }
        if (customer instanceof HumanResource) {
            update = humanResourceMapper.update((HumanResource) customer);
        }
        if (update!=1) {
            return "{\"modifyResult\":0}";
        } else {
            return "{\"modifyResult\":1}";
        }
    }

    /**
     * 查询招聘者个人信息
     * @param id 账号
     * @return json串，没有密码，身份证号加密
     */
    public String getUserInfo(String id) {
        UserInfo userInfo = userInfoMapper.search(id);
        StringBuffer sb = new StringBuffer(userInfo.getIDNO());
        if (sb.length()>5) {
            for(int i=3; i<sb.length()-2; i++){
                sb.setCharAt(i,'*');
            }
        }
        userInfo.setIDNO(sb.toString());
        userInfo.setPswd(null);
        return JSON.toJSONString(userInfo);
    }

    /**
     * 查询招聘者个人信息
     * @param id 账号
     * @return json串，没有密码，带企业名
     */
    public String getHrInfo(String id) {
        HumanResource humanResource = humanResourceMapper.search(id);
        humanResource.setPswd(null);
        Enterprise enterprise = enterpriseMapper.searchById(humanResource.getEnterpriseId());
        humanResource.setEnterpriseName(enterprise.getName());
        return JSON.toJSONString(humanResource);
    }

    /**
     * 根据时间造一个token，加个锁免得大家都来
     * @return token
     */
    private synchronized long getToken() {
        return System.currentTimeMillis();
    }
}
