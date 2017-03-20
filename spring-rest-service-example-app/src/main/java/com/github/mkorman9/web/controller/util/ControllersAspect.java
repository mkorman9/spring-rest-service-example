package com.github.mkorman9.web.controller.util;

import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Aspect
public class ControllersAspect {
    private Validator validator;

    @Autowired
    public ControllersAspect(Validator validator) {
        this.validator = validator;
    }

    @Pointcut("execution(public com.github.mkorman9.web.form.response.ResponseForm *(..))")
    public void requestMappingMethod() { }

    @Around("requestMappingMethod()")
    public ResponseForm validatePayload(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();
        Optional<Object> entityToValidate = findRequestBodyToValidate(
                arguments,
                getAnnotationsFromMethodParameters(method)
        );

        if (entityToValidate.isPresent()) {
            Set<ConstraintViolation<Object>> violations = validator.validate(entityToValidate.get());
            if (!violations.isEmpty()) {
                throw new InvalidInputDataException(createErrorsList(violations));
            }
        }

        return (ResponseForm) joinPoint.proceed();
    }

    private List<ResponseError> createErrorsList(Set<ConstraintViolation<Object>> violations) {
        return violations.stream()
                .map(error -> ResponseError.builder()
                                .message(error.getMessage())
                                .field(error.getPropertyPath().toString())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private Optional<Object> findRequestBodyToValidate(Object[] arguments, Annotation[][] annotationsOverArguments) {
        for (int i = 0; i < arguments.length; i++) {
            for (Annotation annotation : annotationsOverArguments[i]) {
                if (annotation instanceof RequestBody) {
                    return Optional.of(arguments[i]);
                }
            }
        }

        return Optional.empty();
    }

    private Annotation[][] getAnnotationsFromMethodParameters(MethodSignature method) throws NoSuchMethodException {
        return method.getDeclaringType()
                .getMethod(method.getName(), method.getParameterTypes())
                .getParameterAnnotations();
    }
}
