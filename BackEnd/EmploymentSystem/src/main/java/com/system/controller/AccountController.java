package com.system.controller;

import com.system.entity.HumanResource;
import com.system.entity.UserInfo;
import com.system.service.AccountService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 招聘者注册
     */
    @PostMapping(path = "/recruiter/signUp")
    public String userSignUp(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone) {
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
    @PostMapping(path = "/hr/signUp")
    public String hrSignUp(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String enterpriseName,
                           @RequestHeader String code, @RequestHeader String phone) {
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
    @PostMapping(path = "/recruiter/modify")
    public String userModify(@RequestHeader String id, @RequestHeader String password,
                             @RequestHeader String name, @RequestHeader String IDNO,
                             @RequestHeader String phone, @RequestHeader String email,
                             @RequestHeader int age, @RequestBody List<String> interest) {
        // TODO: 密码的解码
        log.info("Receive userModify: id="+id+", pswd="+password+", name="+name+", IDNO="+IDNO+", " +
                "phone="+phone+", email="+email+", age="+age+", interest="+interest);
        StringBuffer sb = new StringBuffer();
        sb.append(" ");
        if (interest!=null && interest.size()>=0) {
            for(String str: interest) {
                sb.append(str).append(" ");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        String interestStr = sb.toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPswd(password);
        userInfo.setName(name);
        userInfo.setIDNO(IDNO);
        userInfo.setPhone(phone);
        userInfo.setEmail(email);
        userInfo.setAge(age);
        userInfo.setInterest(interestStr);
        String json = accountService.modify(userInfo);
        log.info("Result:"+json);
        return json;
    }

    /**
     * hr修改个人信息
     */
    @PostMapping(path = "/hr/modify")
    public String hrModify(@RequestHeader String id, @RequestHeader String password,
                           @RequestHeader String name, @RequestHeader String phone,
                           @RequestHeader String email) {
        // TODO: 密码的解码
        log.info("Receive hrModify: id="+id+", pswd="+password+", name="+name+
                "phone="+phone+", email="+email);
        HumanResource humanResource = new HumanResource();
        humanResource.setId(id);
        humanResource.setPswd(password);
        humanResource.setName(name);
        humanResource.setPhone(phone);
        humanResource.setEmail(email);
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
    @PostMapping(path = "/resume")
    public String uploadResume(@RequestHeader long token,
                               @RequestParam("resume") MultipartFile file) {
        // TODO: 还没写完
        return null;
    }

    /**
     * 招聘者下载个人简历
     * @param token 用户标识
     * @param response
     */
    @GetMapping(path = "/resume")
    public void downloadResume(@RequestHeader long token,
                               HttpServletResponse response) {
        //TODO: 还没写完

    }


}
