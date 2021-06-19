package com.system.service;

import com.alibaba.fastjson.JSON;
import com.system.entity.*;
import com.system.mapper.EnterpriseMapper;
import com.system.mapper.PositionMapper;
import com.system.mapper.ResumeMapper;
import com.system.mapper.UserInfoMapper;
import com.system.util.DownloadUtil;
import com.system.util.TokenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

//    String absolutePath = "/root/resumes/";
    String absolutePath = this.getClass().getResource("/").getPath();
    /**
     * 招聘者上传个人简历
     * @param token
     * @param file
     * @return
     */
    @SneakyThrows
    public String sendSelfResume(String token, MultipartFile file){
        StringBuilder sb = new StringBuilder();
        int resumeResult = 0;
        String userId = tokenUtil.check(token);
        String encode = java.net.URLEncoder.encode(file.getOriginalFilename(),"utf-8");
        String resumeAddress = absolutePath + "selfResumes/"+encode;
        log.info(resumeAddress);
        File newFile = new File(resumeAddress);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        try{
            file.transferTo(newFile);
            String originAddress = userInfoMapper.searchResume(userId);
            List<String> resumes = resumeMapper.selectResumes(userId);
            if(!resumes.contains(originAddress)){
                File oldFile = new File(originAddress);
                if(oldFile.exists()){
                    oldFile.delete();
                }
            }
            UserInfo userInfo = userInfoMapper.search(userId);
            userInfo.setResume(resumeAddress);
            int count = userInfoMapper.updateResume(userInfo);
            if(count == 0){
                resumeResult = 1;
            }
        }catch (IOException e){
            e.printStackTrace();
            resumeResult = 1;
        }
        sb.append("{\"resumeResult\":"+resumeResult+"}");
        return sb.toString();
    }

    /**
     * 招聘者下载个人简历
     * @param token
     * @return
     */
    public String downloadSelfResume(String token,HttpServletResponse response){
        String userId = tokenUtil.check(token);
        String path = userInfoMapper.searchResume(userId);
        int index = path.lastIndexOf("/");
        String fileName = path.substring(index+1);
        String result = DownloadUtil.download(fileName, path, response);
        return result;
    }
    /**
     * 招聘者查看已经投递的简历信息
     * @param token
     * @return
     */
    public String getSendResumeInfo(String token){
        List<SendResumeInfo> sendResumes = new ArrayList<>();
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
        int hasPrivilege = 0;
        String hrId = positionInfo.getHrId();
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
            hasPrivilege = 0;
       }else{
            hasPrivilege = 1;
        }
        return "{\"hasPrivilege\":"+hasPrivilege+",\"resumeList\":"+JSON.toJSONString(receiveResumes)+"}";
    }

    /**
     * 招聘者上传非个人简历的简历
     * @param token
     * @param file
     * @return
     */
    @SneakyThrows
    public String uploadResume(String token, MultipartFile file){
        StringBuilder sb = new StringBuilder();
        int resumeResult = 1;
        String qualifier = "";
        if(!file.isEmpty()){
            String id = tokenUtil.check(token);
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            String fileName = file.getOriginalFilename().substring(0,dotPos);
            String suffix = file.getOriginalFilename().substring(dotPos+1);
            qualifier = fileName + "$" + System.currentTimeMillis() + "$" + suffix;
            qualifier = URLEncoder.encode(qualifier, "utf-8");
            String wholeName = qualifier + "." + suffix;
            String resumeAddress = absolutePath+"nowResumes/"+wholeName;
            log.info(resumeAddress);
            File newFile = new File(resumeAddress);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            try{
                file.transferTo(newFile);
                resumeResult = 0;
            }catch (IOException e){
                e.printStackTrace();
                resumeResult = 1;
            }
        }
        sb.append("{\"resumeResult\":"+resumeResult+",\"qualifier\":\""+qualifier+"\"}");
        return sb.toString();
    }

    /**
     * 招聘者投递简历
     * @param token
     * @param useSelf
     * @param positionId
     * @param qualifier
     * @return
     */
    public String sendResume(String token,boolean useSelf,long positionId,String qualifier){
        StringBuilder sb = new StringBuilder();
        String userId = tokenUtil.check(token);
        Resume exResume = resumeMapper.searchByIds(new ResumeId(positionId, userId));
        String resumeAddress = null;
        int sendResult = 0;
        if(useSelf){
            resumeAddress = userInfoMapper.searchResume(userId);
        }else{
            if(qualifier != null) {
                String wholeName = qualifier + "." + qualifier.split("$")[2];
                resumeAddress = absolutePath + "nowResumes/" + wholeName;
            }else{
                sendResult = 1;
            }
        }
        if(exResume == null){
            //没投过简历，插入简历
            Resume nowResume = new Resume();
            nowResume.setPositionId(positionId);
            nowResume.setUserId(userId);
            nowResume.setResume(resumeAddress);
            nowResume.setDealed(false);
            int count = resumeMapper.insertResume(nowResume);
            if(count == 0){
                sendResult = 1;
            }
        }else{
            //投过简历，更新简历
            exResume.setResume(resumeAddress);
            int count = resumeMapper.updateResume(exResume);
            if(count == 0){
                sendResult = 1;
            }
        }
        sb.append("{\"sendResult\":"+sendResult+"}");
        return sb.toString();
    }

    /**
     * 招聘者下载上传过的简历
     * @param token
     * @param positionId
     * @param response
     * @return
     */
    public String userDownloadResume(String token,long positionId,HttpServletResponse response){
        String userId = tokenUtil.check(token);
        Resume resume = resumeMapper.searchByIds(new ResumeId(positionId, userId));
        String resumeAddress = resume.getResume();
        int firstIndex = resumeAddress.lastIndexOf("/");
        int lastIndex = resumeAddress.lastIndexOf(".");
        String substr = resumeAddress.substring(firstIndex+1,lastIndex);
        String fileName = null;
        if(!substr.contains("$")){ //个人简历
            fileName = resumeAddress.substring(firstIndex+1);
        }else{
            String[] addressArray = substr.split("$");
            fileName = addressArray[0]+"."+ addressArray[2];
        }
        String message = DownloadUtil.download(fileName,resumeAddress,response);
        return message;
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

    /**
     * hr下载简历
     * @param token
     * @param positionId
     * @param id
     * @return
     */
    public String hrDownloadResume(String token,long positionId,String id,HttpServletResponse response){
        String message = null;
        String hrId = tokenUtil.check(token);
        PositionInfo positionInfo = positionMapper.getPositionInfo(positionId);
        String phrId = positionInfo.getHrId();
        if(!hrId.equals(phrId)){
            message = "You have no privilege to download";
        }else{
            Resume resume = resumeMapper.searchByIds(new ResumeId(positionId, id));
            String resumeAddress = resume.getResume();
            int firstIndex = resumeAddress.lastIndexOf("/");
            int lastIndex = resumeAddress.lastIndexOf(".");
            String substr = resumeAddress.substring(firstIndex+1,lastIndex);
            String fileName = null;
            if(!substr.contains("$")){ //个人简历
                fileName = resumeAddress.substring(firstIndex+1);
            }else{
                String[] addressArray = substr.split("$");
                fileName = addressArray[0]+"."+ addressArray[2];
            }
            message = DownloadUtil.download(fileName,resumeAddress,response);
        }
        return message;
    }
}
