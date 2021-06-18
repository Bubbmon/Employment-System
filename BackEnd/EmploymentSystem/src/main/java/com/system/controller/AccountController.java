package com.system.controller;

import com.system.entity.HumanResource;
import com.system.entity.UserInfo;
import com.system.interceptor.NeedVerify;
import com.system.service.AccountService;
import com.system.service.ResumeService;
import com.system.util.DecodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
    @PostMapping(path = "/recruiter/signUp", produces = "application/json;charset=utf-8")
    public String userSignUp(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone) {
        name = DecodeUtil.decode(name);
        // TODO: 密码的解码
        log.info("Receive userSignUp: id="+id+", pswd="+password+", name="+name+", IDNO="+IDNO+", phone="+phone);
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
    @PostMapping(path = "/hr/signUp", produces = "application/json;charset=utf-8")
    public String hrSignUp(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String enterpriseName,
                           @RequestHeader String code, @RequestHeader String phone) {
        name = DecodeUtil.decode(name);
        enterpriseName = DecodeUtil.decode(enterpriseName);
        // TODO: 密码的解码
        log.info("Receive hrSignUp: id="+id+", pswd="+password+", name="+name+", enterpriseName"+enterpriseName+", code"+code+", phone="+phone);
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
    @PostMapping(path = "/signIn", produces = "application/json;charset=utf-8")
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
    @NeedVerify
    @PostMapping(path = "/recruiter/modify", produces = "application/json;charset=utf-8")
    public String userModify(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone, @RequestHeader String email,
                             @RequestHeader int age, @RequestHeader String interest) {
        name = DecodeUtil.decode(name);
        // TODO: 密码的解码
        log.info("Receive userModify: id="+id+", pswd="+password+", name="+name+", IDNO="+IDNO+", " +
                "phone="+phone+", email="+email+", age="+age+", interest="+interest);
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
    @NeedVerify
    @PostMapping(path = "/hr/modify", produces = "application/json;charset=utf-8")
    public String hrModify(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String phone,
                           @RequestHeader String email, @RequestHeader String department) {
        name = DecodeUtil.decode(name);
        department = DecodeUtil.decode(department);
        // TODO: 密码的解码
        log.info("Receive hrModify: id="+id+", pswd="+password+", name="+name+
                "phone="+phone+", email="+email);
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
    @GetMapping(path = "/recruiter/info/{id}", produces = "application/json;charset=utf-8")
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
    @GetMapping(path = "/hr/info/{id}", produces = "application/json;charset=utf-8")
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
    @NeedVerify
    @PostMapping(path = "/resume", produces = "application/json;charset=utf-8")
    public String uploadResume(@RequestHeader String token,
                               @RequestParam("resume") MultipartFile file) {
        log.info("Receive token:"+token+",file:"+file.getOriginalFilename());
        String uploadResult = resumeService.sendSelfResume(token, file);
        log.info("Get upload self resume result:"+uploadResult);
        return uploadResult;
    }

    /**
     * 招聘者下载个人简历
     * @param token 用户标识
     * @param response
     */
    @NeedVerify
    @GetMapping(path = "/resume")
    public void downloadResume(@RequestHeader String token,
                               HttpServletResponse response) {
        log.info("Receive token:"+token);
        String downloadResult = resumeService.downloadSelfResume(token, response);
        log.info("Get self resume download result:"+downloadResult);
    }


}
