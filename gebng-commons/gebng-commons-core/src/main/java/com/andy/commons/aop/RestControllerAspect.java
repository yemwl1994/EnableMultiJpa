package com.andy.commons.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(RestControllerAspect.class);
    @Pointcut("execution(public * com.andy..api.*.*(..))")
    public void restControllerAspect() {}


    @Around("restControllerAspect()")
    public Object invokeCommand(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.debug("Enter to restControllerAspect start");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String target = signature.getDeclaringType().getSimpleName() + "."
                + signature.getName() + "." + signature.getMethod();

        logger.info("Returning type: " + signature.getReturnType());

        logger.info("Start " + target);

        return joinPoint.proceed();


    }
}
