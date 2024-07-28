package com.dimstyl.dietitianhub.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.dimstyl.dietitianhub.services.impl.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(public * com.dimstyl.dietitianhub.security.CustomUserDetailsService.loadUserByUsername(..))")
    public void userDetailsServiceMethod() {
    }

    @AfterThrowing(pointcut = "serviceMethods() || userDetailsServiceMethod()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();

        // Log the exception message
        log.error("Exception thrown in method: {}, exception message: {}", methodName, ex.getMessage());

        // Check if the exception has a cause and log it
        Throwable cause = ex.getCause();
        if (cause != null) log.error("Cause of the exception: {}", cause.getMessage());

        // Re-throw the exception to maintain the original behavior
        throw ex;
    }

}
