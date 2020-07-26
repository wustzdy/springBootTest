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
    @Pointcut("(execution(public * com.example.springboot.demo.springboottest.controller.*(..)))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws UnsupportedEncodingException {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        log.info("========================= before -- start =========================");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        log.info("CLASS_METHOD : " + methodSignature.getDeclaringTypeName() + "." + methodSignature.getName());

        log.info("请求参数:    名称     值");
        String[] argsNameArray = methodSignature.getParameterNames();
        Object[] argsValueArray = joinPoint.getArgs();
        if (argsNameArray == null || argsNameArray.length < 1) {
            log.info("args_name: null");
            log.info("args_value: null");
        } else {
            for (int i = 0; i < argsNameArray.length; i++) {
                log.info("args_name: " + argsNameArray[i]);

                String argValue = argsValueArray[i] != null ? argsValueArray[i].toString() : "";
                if (argsNameArray[i].contains("encode")) {
                    String str = URLDecoder.decode(argValue, "utf-8");
                    log.info("args_value: " + (str.length() > 200 ? str.substring(0, 200) + "..." : str));
                } else {
                    log.info("args_value: " + (argValue.length() > 200 ? argValue.substring(0, 200) + "..." : argValue));
                }
            }
        }

        // 记录下请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.info("========================= before -- end =========================");
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        log.info("IP : " + request.getRemoteAddr());

        log.info("========================= before -- end =========================");
    }

    @AfterReturning(returning = "rvt", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object rvt) {
        log.info("========================= after -- start =========================");
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());

        if (rvt != null) {
            String str = rvt.toString();
            if (str.length() > 200) {
                str = str.substring(0, 200) + "...";
            }

            log.info("return 返回值:");
            log.info(str);
        }

        log.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));

        log.info("========================= after -- end =========================");
    }
}