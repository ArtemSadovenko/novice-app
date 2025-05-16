package com.novice.debatenovice.aspects;

import com.novice.debatenovice.annotations.CurrentUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CurrentUserAspect extends SecurityHandlerAspect {
    @Around("execution(* *(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object injectCurrentUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        java.lang.annotation.Annotation[][] paramAnnotations = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            for (java.lang.annotation.Annotation annotation : paramAnnotations[i]) {
                if (annotation instanceof CurrentUser) {
                    // Only inject if parameter is annotated with @CurrentUser
                    args[i] = super.getCurrentUser();
                    break;
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
