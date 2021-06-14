package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.*;
import com.system.mapper.EnterpriseMapper;
import com.system.mapper.PositionMapper;
import com.system.mapper.ResumeMapper;
import com.system.mapper.UserInfoMapper;
import com.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeService{

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    ResumeMapper resumeMapper;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 招聘者查看已经投递的简历信息
     * @param token
     * @return
     */
    public String getSendResumeInfo(String token){
        List<SendResumeInfo> sendResumes = new ArrayList<>();
        //TODO:区分hr和招聘者
        if(token.charAt(0) == 'u'){
            String id = tokenUtil.check(token);
            List<Resume> resumes = resumeMapper.searchByUserId(id);
            for(Resume resume : resumes){
                long positionId = resume.getPositionId();
                boolean isDealed = resume.isDealed();
                PositionInfo positionInfo = positionMapper.getPositionInfo(positionId);
                Enterprise enterprise = enterpriseMapper.searchById(positionInfo.getEnterpriseId());
                SendResumeInfo sendResumeInfo = new SendResumeInfo();
                sendResumeInfo.setPositionId(positionId);
                sendResumeInfo.setPositionTitle(positionInfo.getTitle());
                sendResumeInfo.setEnterpriseName(enterprise.getName());
                sendResumeInfo.setDealed(isDealed);
                sendResumes.add(sendResumeInfo);
            }
        }
        return JSON.toJSONString(sendResumes);
    }

    /**
     * hr查看某招聘位置收到的简历列表
     * @param token
     * @param positionId
     * @return
     */
    public String getHrResumes(String token,long positionId){
        List<ReceiveResumeInfo> receiveResumes = new ArrayList<>();
        PositionInfo positionInfo = positionMapper.getPositionInfo(positionId);
        String hrId = positionInfo.getHrId();
        //TODO:不是发布公告的HR怎么办
        if(tokenUtil.check(token).equals(hrId)){
            List<Resume> resumes = resumeMapper.searchByPositionId(positionId);
            for(Resume resume : resumes){
                String uid = resume.getUserId();
                boolean isDealed = resume.isDealed();
                UserInfo userInfo = userInfoMapper.search(uid);
                ReceiveResumeInfo receiveResume = new ReceiveResumeInfo();
                receiveResume.setId(uid);
                receiveResume.setName(userInfo.getName());
                receiveResume.setPhone(userInfo.getPhone());
                receiveResume.setDealed(isDealed);
                receiveResumes.add(receiveResume);
          }
       }
        return JSON.toJSONString(receiveResumes);
    }

    /**
     * hr处理简历
     * @param token
     * @param positionId
     * @param id
     * @param isDealed
     * @return
     */
    public String dealResume(String token,long positionId,String id,boolean isDealed){
         StringBuilder sb = new StringBuilder();
         sb.append("{\"dealResult\":1}");
         String hrId = tokenUtil.check(token);
         String shrId = positionMapper.getPositionInfo(positionId).getHrId();
         if(hrId.equals(shrId)) {
             Resume resume = resumeMapper.searchByIds(new ResumeId(positionId, id));
             resume.setDealed(isDealed);
             int modifyResult = resumeMapper.modifyDeal(resume);
             if (modifyResult == 1) {
                 sb.setCharAt(sb.length() - 2, '0');
             }
         }
         return sb.toString();
    }
}
