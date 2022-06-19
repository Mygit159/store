package com.cy.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component//将当前类的对象创建使用维护交由Spring容器维护
@Aspect //将当前类标记为切面类
public class TimeAspect {
    /*
    可以将此方法的信息保存到数据库，供后期的运维工程师实时监控
     */
    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //先记录当前时间
        long start=System.currentTimeMillis();
        Object result = pjp.proceed();
        //记录方法执行后的当前时间
        Long end=System.currentTimeMillis();
        System.out.println(pjp.getSignature()+"耗时："+(end-start));
        return result;
    }
}
