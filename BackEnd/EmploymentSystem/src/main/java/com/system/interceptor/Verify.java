package com.system.interceptor;

import com.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-16 13:07
 **/
@Component
@Aspect
@Slf4j
public class Verify {

    @Value("${code.notLogInCode}")
    private Integer notLogInCode;
    @Value("${code.notLogInMessage}")
    private String notLogInMessage;
    @Value("${code.logInExpiredCode}")
    private Integer logInExpiredCode;
    @Value("${code.logInExpiredMessage}")
    private String logInExpiredMessage;

    @Autowired
    TokenUtil tokenUtil;

    @Pointcut("@annotation(com.system.interceptor.NeedVerify)")
    public void NeedVerify(){ }

    @Around("NeedVerify()")
    public String verifyAccount(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String token = request.getHeader("token");
        if(token==null) {
            return "{\"error_code\":"+notLogInCode+", \"error_msg\":\""+notLogInMessage+"\"}";
        }else if(tokenUtil.check(token)==null){
            return "{\"error_code\":"+logInExpiredCode+", \"error_msg\":\""+logInExpiredMessage+"\"}";
        }
        return (String) joinPoint.proceed();
    }

}
