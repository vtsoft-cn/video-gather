package com.vtsoft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * description
 * 请求访问日志打印
 *
 * @author Garden
 * @version 1.0 create 2023/2/23 22:05
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    /**
     * controller方法切面
     */
    @Pointcut("execution(* com.vtsoft.controller..*.*(..))")
    public void execute() {
    }

    @Around(value = "execute()")
    public Object aspectOfController(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取参数值
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            log.info("接口访问: {}", ((ServletRequestAttributes) requestAttributes).getRequest().getRequestURI());
        }
        return joinPoint.proceed();
    }
}
