package com.example.springboot.demo.springboottest.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 日志打印
 *
 * @author zdty
 * @version 1.0
 * @date 2020-04-07 15:32:08
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Around("execution(public * com.example.springboot.demo.springboottest.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        log.info("请求方法 : {}.{}", signature.getDeclaringTypeName(), signature.getName());
        Object[] objArray = joinPoint.getArgs();
        int i = 0;
        for (Object obj : objArray) {
            log.info("请求参数[{}] : {}", i++, JSONObject.toJSONString(obj));
        }
        Object obj = joinPoint.proceed();
        log.info("返回参数 : " + JSONObject.toJSONString(obj));
        return obj;
    }
}