package com.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
@Aspect
@Component
public class Log {
    @Pointcut("@within(com.system.interceptor.LogMe)")
    public void log(){}

    @Around("log()")
    public String letMeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("before controller:====================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = request.getSession().getId();
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        while(headers.hasMoreElements()){
            String name = headers.nextElement();
            sb.append(name).append(":").append(request.getHeader(name)).append(" ");
        }
        log.info("[sessionId:"+sessionId+"]");
        log.info("["+sessionId+"]request: "+request.getMethod()+" "+ request.getRequestURL());
        log.info("["+sessionId+"]headers: "+sb.toString());
        log.info("["+sessionId+"]signature: "+joinPoint.getSignature().toShortString());
        log.info("["+sessionId+"]start proceed===");
        String result = (String) joinPoint.proceed();
        log.info("["+sessionId+"]already proceeded, result: "+result);
        return result;
    }
}
