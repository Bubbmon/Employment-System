package com.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: EmploymentSystem
 * @description:
 * @author: LiLi
 * @create: 2021-06-19 14:04
 **/
@Slf4j
@Component
public class Log {
    @Pointcut("@annotation(com.system.interceptor.LogMe)")
    public void log(){}

    @Around("log()")
    public String letMeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("before controller:====================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        while(headers.hasMoreElements()){
            String name = headers.nextElement();
            sb.append(name).append(":").append(request.getHeader(name)).append(" ");
        }
        log.debug("headers:"+sb.toString());
        log.debug("request: "+request.getPathInfo());
        log.debug("signature= "+joinPoint.getSignature().toShortString());
        String result = (String) joinPoint.proceed();
        log.debug("result: "+result);
        return result;
    }
}
