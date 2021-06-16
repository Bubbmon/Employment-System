package com.system.controller;

import com.system.Check.NeedLogIn;
import com.system.entity.HumanResource;
import com.system.entity.UserInfo;
import com.system.service.AccountService;
import com.system.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @Author Legion
 * @Date 2021/6/12 15:19
 * @Description 账号相关的请求（注册，登陆，查询资料，修改资料，个人简历上传下载）
 */
@RestController
@RequestMapping("/account")
@Slf4j
@CrossOrigin("*")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ResumeService resumeService;

    /**
     * 招聘者注册
     */
    @PostMapping(path = "/recruiter/signUp")
    public String userSignUp(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone) {
        // TODO: 密码的解码
        log.info("Receive userSignUp: id="+id+", pswd="+password+", name="+name+", IDNO="+IDNO+", phone="+phone);
        try {
            name = URLDecoder.decode(name,"utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPswd(password);
        userInfo.setName(name);
        userInfo.setIDNO(IDNO);
        userInfo.setPhone(phone);
        String json = accountService.signUp(userInfo);
        log.info("Result:"+json);
        return json;
    }

    /**
     * hr注册
     */
    @PostMapping(path = "/hr/signUp")
    public String hrSignUp(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String enterpriseName,
                           @RequestHeader String code, @RequestHeader String phone) {
        // TODO: 密码的解码
        log.info("Receive hrSignUp: id="+id+", pswd="+password+", name="+name+", enterpriseName"+enterpriseName+", code"+code+", phone="+phone);
        try {
            name = URLDecoder.decode(name,"utf-8");
            enterpriseName = URLDecoder.decode(enterpriseName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException");
        }
        HumanResource humanResource = new HumanResource();
        humanResource.setId(id);
        humanResource.setPswd(password);
        humanResource.setName(name);
        humanResource.setEnterpriseName(enterpriseName);
        humanResource.setCode(code);
        humanResource.setPhone(phone);
        String json = accountService.signUp(humanResource);
        log.info("Result:"+json);
        return json;
    }

    /**
     * 登陆
     */
    @PostMapping(path = "/signIn")
    public String signIn(@RequestHeader String id, @RequestHeader String password,
                         @RequestHeader boolean isRecruiter) {
        // TODO: 密码的解码
        log.info("Receive signIn: id="+id+", pswd="+password+", isRecuiter="+isRecruiter);
        String json = accountService.signIn(id, password, isRecruiter);
        log.info("Result:"+json);
        return json;
    }

    /**
     * 招聘者修改个人信息
     */
    @NeedLogIn
    @PostMapping(path = "/recruiter/modify")
    public String userModify(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone, @RequestHeader String email,
                             @RequestHeader int age, @RequestHeader String interest) {
        // TODO: 密码的解码
        log.info("Receive userModify: id="+id+", pswd="+password+", name="+name+", IDNO="+IDNO+", " +
                "phone="+phone+", email="+email+", age="+age+", interest="+interest);
        try {
            name = URLDecoder.decode(name,"utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPswd(password);
        userInfo.setName(name);
        userInfo.setIDNO(IDNO);
        userInfo.setPhone(phone);
        userInfo.setEmail(email);
        userInfo.setAge(age);
        userInfo.setInterest(interest);
        String json = accountService.modify(userInfo);
        log.info("Result:"+json);
        return json;
    }

    /**
     * hr修改个人信息
     */
    @NeedLogIn
    @PostMapping(path = "/hr/modify")
    public String hrModify(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String phone,
                           @RequestHeader String email, @RequestHeader String department) {
        // TODO: 密码的解码
        log.info("Receive hrModify: id="+id+", pswd="+password+", name="+name+
                "phone="+phone+", email="+email);
        try {
            name = URLDecoder.decode(name,"utf-8");
            department = URLDecoder.decode(department);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException");
        }
        HumanResource humanResource = new HumanResource();
        humanResource.setId(id);
        humanResource.setPswd(password);
        humanResource.setName(name);
        humanResource.setPhone(phone);
        humanResource.setEmail(email);
        humanResource.setDepartment(department);
        String json = accountService.modify(humanResource);
        log.info("Result:"+json);
        return json;
    }

    /**
     * 查询招聘人信息
     * @param id 招聘人账户id
     * @return
     */
    @GetMapping(path = "/recruiter/info/{id}")
    public String getUserInfo(@PathVariable("id")String id) {
        log.info("Receive getUserInfo: id="+id);
        String json = accountService.getUserInfo(id);
        log.info("Result:" +json);
        return json;
    }

    /**
     * 查询hr信息
     * @param id hr账户id
     * @return
     */
    @GetMapping(path = "/hr/info/{id}")
    public String getHrInfo(@PathVariable("id") String id) {
        log.info("Receive getHrInfo: id="+id);
        String json = accountService.getHrInfo(id);
        log.info("Result:" +json);
        return json;
    }

    /**
     * 招聘者上传个人简历
     * @param token 用户标识
     * @param file 简历数据
     * @return
     */
    @NeedLogIn
    @PostMapping(path = "/resume")
    public String uploadResume(@RequestHeader String token,
                               @RequestParam("resume") MultipartFile file) {
        log.info("Receive token:"+token+",file:"+file.getOriginalFilename());
        String uploadResult = resumeService.sendSelfResume(token, file);
        log.info("Get upload self resume result:"+uploadResult);
        return null;
    }

    /**
     * 招聘者下载个人简历
     * @param token 用户标识
     * @param response
     */
    @NeedLogIn
    @GetMapping(path = "/resume")
    public void downloadResume(@RequestHeader String token,
                               HttpServletResponse response) {
        log.info("Receive token:"+token);
        String downloadResult = resumeService.downloadSelfResume(token, response);
        log.info("Get self resume download result:"+downloadResult);
    }


}
