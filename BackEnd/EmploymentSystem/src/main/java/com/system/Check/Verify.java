package com.system.Check;

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

    @Value("code.notLogInCode")
    private static Integer notLogInCode;
    @Value("code.notLogInMessage")
    private static String notLogInMessage;
    @Value("code.logInExpiredCode")
    private static Integer logInExpiredCode;
    @Value("code.logInExpiredMessage")
    private static String logInExpiredMessage;

    @Autowired
    TokenUtil tokenUtil;

    @Pointcut("@annotation(com.system.Check.NeedLogIn)")
    public void NeedLogIn(){ }

    @Around("NeedLogIn()")
    public String verifyAccount(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String token = request.getHeader("token");
        if(token==null) {
            response.sendError(notLogInCode,notLogInMessage);
            return "";
        }else if(tokenUtil.check(token)==null){
            response.sendError(logInExpiredCode,logInExpiredMessage);
            return "";
        }
        return (String) joinPoint.proceed();
    }

//    @Pointcut("@annotation(com.system.Check.HrBelongsEnterpriseCheck)")
//    public void hrBelongsEnterpriseCheck(){
//    }

}
