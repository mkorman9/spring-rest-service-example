package com.github.mkorman9.web.controller.util;

import com.github.mkorman9.web.form.response.ResponseForm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Optional;

@Component
@Aspect
public class ControllersAspect {
    @Pointcut("execution(public com.github.mkorman9.web.form.response.ResponseForm *(..))")
    public void requestMappingMethod() { }

    @Around("requestMappingMethod()")
    public ResponseForm validatePayload(ProceedingJoinPoint joinPoint) throws Throwable {
        ControllersCommons controller = (ControllersCommons) joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        Optional<BindingResult> bindingResultOptional = findBindingResults(arguments);

        if (bindingResultOptional.isPresent()) {
            BindingResult bindingResult = bindingResultOptional.get();
            if (bindingResult.hasErrors()) {
                return controller.handleBindingError(bindingResult);
            }
        }

        return (ResponseForm) joinPoint.proceed();
    }

    private Optional<BindingResult> findBindingResults(Object[] arguments) {
        return Arrays.stream(arguments)
                .filter(arg -> arg instanceof BindingResult)
                .map(arg -> (BindingResult) arg)
                .findFirst();
    }
}
