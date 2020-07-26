package com.example.springboot.demo.springboottest.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 定义在web包或者子包
     * ~ 第四个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
   /* @Pointcut("(execution(public * com.example.springboot.demo.springboottest.controller.*(..)))")
    public void webLog() {
    }*/

    //Controller层切点
    @Pointcut("execution (public * com.example.springboot.demo.springboottest.controller..*.*(..))")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("请求钱before。。。。。。。。。。。。。。。。。。。。。");
        /*用isDebugEnabled方法判断下是能提升性能的*/
        if (log.isInfoEnabled()) {
            log.info("before " + joinPoint);
        }
    }
}