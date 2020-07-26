package com.example.springboot.demo.springboottest.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

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

    //Controller层切点
    @Pointcut("execution (public * com.example.springboot.demo.springboottest.controller..*.*(..))")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        log.info("========================= before -- start =========================");
        /*用isDebugEnabled方法判断下是能提升性能的*/
        if (log.isInfoEnabled()) {
            log.info("before " + joinPoint);
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 记录下请求内容
        log.info("################URL : " + request.getRequestURL().toString());
        log.info("################HTTP_METHOD : " + request.getMethod());
        log.info("################IP : " + request.getRemoteAddr());
        log.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        log.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象

        log.info("IP : " + request.getRemoteAddr());
        log.info("========================= before -- end =========================");
    }

    @Around(value = "controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("========================= around -- start =========================");
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String logName = targetName + "." + methodName;
        Object[] args = joinPoint.getArgs();

        log.info("Around通知+begin {}, args: {}", logName, args);
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            log.info("Around通知+end {}, result: {}", logName, obj);
        } catch (Throwable e) {
            log.info("Around通知+end {}, error: {} ", logName, e.getMessage());
            throw e;
        }
        log.info("========================= around -- end =========================");
        return obj;
    }


    /**
     * 后置通知：目标方法执行之后执行以下方法体的内容，不管是否发生异常。     * @param jp    
     */
    @After("controllerAspect()")
    public void afterMethod(JoinPoint jp) {
        log.info("========================= After -- start =========================");
        log.info("【后置通知After】 +this is a afterMethod advice...");
        log.info("========================= After -- end =========================");
    }

    //    返回通知：目标方法正常执行完毕时执行以下代码     * @param jp     * @param result    
    @AfterReturning(pointcut = "controllerAspect()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("========================= AfterReturning -- start =========================");
        String methodName = joinPoint.getSignature().getName();
        log.info("【返回通知AfterReturning】the method 【" + methodName + "】 ends with 【" + result + "】");

        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());

        if (result != null) {
            String str = result.toString();
            if (str.length() > 200) {
                str = str.substring(0, 200) + "...";
            }

            log.info("return 返回值:");
            log.info(str);
        }

        log.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));

        log.info("========================= after -- end =========================");
    }


    /**
     * 异常通知：目标方法发生异常的时候执行以下代码
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "ex")
    public void afterThorwingMethod(JoinPoint joinPoint, NullPointerException ex) {
        log.info("========================= AfterThrowing -- start =========================");
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);
        log.info("========================= AfterThrowing -- end =========================");
    }
}
/*
https://www.cnblogs.com/wangshen31/p/9379197.html
最后,再记录一下各个不同的advice的拦截顺序的问题.
        情况一:只有一个Aspect类:
        1,无异常：@Around（proceed()之前的部分） →@Before → 方法执行 →@Around（proceed()之后的部分） →@After →@AfterReturning
        2,有异常：@Around（proceed(之前的部分)） →@Before → 扔异常ing →@After →@AfterThrowing
        （大概是因为方法没有跑完抛了异常，没有正确返回所有@Around的proceed()之后的部分和@AfterReturning两个注解的加强没有能够织入）*/
