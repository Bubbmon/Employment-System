package com.system.controller;

import com.system.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Legion
 * @Date 2021/6/12 18:57
 * @Description 投递相关
 */
@Slf4j
@RestController
@RequestMapping("/send")
@CrossOrigin("*")
public class SendController {

    @Autowired
    ResumeService resumeService;

    /**
     * 招聘者查看已经投递的简历信息（摘要）
     * @param token 用户标识
     * @return
     */
    @GetMapping(path = "/recruiter/all")
    public String getUserAllSend(@RequestHeader String token) {
        log.info("Receive token:"+token);
        String sendResumeInfo = resumeService.getSendResumeInfo(token);
        log.info("Get user all send:"+sendResumeInfo);
        return sendResumeInfo;
    }

    /**
     * 招聘者下载投递的简历信息（正文数据）
     * @param token 用户标识
     * @param positionId 招聘信息id
     */
    @GetMapping(path = "/recruiter/download")
    public void sendDownload(@RequestHeader String token, @RequestHeader long positionId) {

    }

    /**
     * 招聘者上传简历信息（非个人简历）
     * @param token 用户标识
     * @param file 简历数据
     * @return 上传成功后会获得临时简历标识
     */
    @PostMapping(path = "/recruiter/upload")
    public String sendUpload(@RequestHeader String token, @RequestParam("resume")MultipartFile file) {
        return null;
    }

    /**
     * 招聘者投递简历
     * @param token 用户标识
     * @param useSelf 是否使用个人简历
     * @param positionId 招聘信息id
     * @param qualifier 临时简历标识
     * @return
     */
    @PostMapping(path = "/recruiter")
    public String send(@RequestHeader String token, @RequestHeader boolean useSelf,
                       @RequestHeader long positionId, @RequestHeader String qualifier) {
        return null;
    }

    /**
     * hr查看某招聘位置收到的简历列表
     * @param token 用户标识
     * @param positionId 招聘信息id
     * @return
     */
    @GetMapping(path = "/hr/{positionId}")
    public String getHrSend(@RequestHeader String token, @PathVariable("positionId")long positionId) {
        log.info("Get token:"+token+" and positionId:"+positionId);
        String resumes = resumeService.getHrResumes(token, positionId);
        log.info("Get hr send result:"+resumes);
        return resumes;
    }

    /**
     * hr下载简历
     * @param token 用户标识
     * @param positionId 招聘信息id
     * @param id 招聘者id
     * @param response
     * @return
     */
    @GetMapping(path = "/hr/{positionId}/{id}")
    public String hrResumeDownload(@RequestHeader String token, @PathVariable("positionId") long positionId,
                                   @PathVariable("id") String id, HttpServletResponse response) {
        return null;
    }

    /**
     * hr处理简历
     * @param token 用户标识
     * @param positionId 招聘信息id
     * @param id 招聘者id
     * @param isDealed 是否已处理
     * @return
     */
    @GetMapping(path = "/hr/deal/{positionId}/{id}")
    public String deal(@RequestHeader String token, @PathVariable("positionId") long positionId,
                       @PathVariable("id") String id, @RequestHeader boolean isDealed) {
        log.info("Receive token:"+token+",positionId:"+positionId+",id:"+id+",isDealed:"+isDealed);
        String dealResult = resumeService.dealResume(token, positionId, id, isDealed);
        log.info("Get resume deal result:"+dealResult);
        return dealResult;
    }
}
