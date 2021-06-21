//package com.system.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
///**
// * @Author Legion
// * @Date 2021/6/19 14:34
// * @Description
// */
//@Component
//@Aspect
//@Slf4j
//public class ControllerLog {
//    @Pointcut("@within(com.system.interceptor.NeedLog)")
//    public void NeedLog(){ }
//
//    @Before("NeedLog()")
//    public void writeBeforeLog(ProceedingJoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String methodName = methodSignature.getName();
//        Object[] args = joinPoint.getArgs();
//        String[] parameters = methodSignature.getParameterNames();
//        if (args.length==parameters.length) {
//            int length = args.length;
//            StringBuffer sb = new StringBuffer("Receive ").append(methodName).append(": ");
//            for(int i=0; i<length; i++){
//                sb.append(parameters[i]).append("=").append(args[i]).append(", ");
//            }
//            sb.deleteCharAt(sb.length()-1);
//            log.info(sb.toString());
//        } else {
//            log.warn("Controller log fail.");
//        }
//    }
//
//    @AfterReturning(returning="rvt", pointcut="NeedLog()")
//    public void writeAfterLog(Object rvt) {
//        log.info("Result: "+rvt.toString());
//    }
//}
