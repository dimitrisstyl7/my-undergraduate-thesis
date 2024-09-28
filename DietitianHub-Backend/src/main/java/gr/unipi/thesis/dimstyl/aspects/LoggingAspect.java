package gr.unipi.thesis.dimstyl.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * gr.unipi.thesis.dimstyl.services.impl.*.*(..))")
    public void servicePackageMethods() {
    }

    @Pointcut("execution(public * gr.unipi.thesis.dimstyl.security.*Service.*(..))")
    public void securityPackageServicesMethods() {
    }

    @Pointcut("execution(public * gr.unipi.thesis.dimstyl.email.EmailServiceImpl.*(..))")
    public void emailServiceImplMethods() {
    }

    @Pointcut("servicePackageMethods() || securityPackageServicesMethods() || emailServiceImplMethods()")
    public void serviceLayerMethods() {
    }

    @Pointcut("execution(public * gr.unipi.thesis.dimstyl.scheduling.AppointmentStatusScheduler.markScheduledAppointmentsBeforeNowAsCompleted(..))")
    public void markScheduledAppointmentsBeforeNowAsCompletedMethod() {
    }

    @AfterThrowing(pointcut = "serviceLayerMethods()", throwing = "ex")
    public void logServiceLayerException(JoinPoint joinPoint, Throwable ex) throws Throwable {
        String methodName = joinPoint.getSignature().toLongString();

        // Log the exception message
        log.error("Exception thrown in method: {}, exception message: {}", methodName, ex.getMessage());

        // Check if the exception has a cause and log it
        Throwable cause = ex.getCause();
        if (cause != null) log.error("Cause of the exception: {}", cause.getMessage());

        // Re-throw the exception to maintain the original behavior
        throw ex;
    }

    @Before("markScheduledAppointmentsBeforeNowAsCompletedMethod()")
    public void logScheduledMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Scheduled task: Updating status of past appointments in method: {}", methodName);
    }

}
