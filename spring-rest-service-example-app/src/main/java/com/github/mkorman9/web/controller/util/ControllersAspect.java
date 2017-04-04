package com.github.mkorman9.web.controller.util;

import com.codepoetics.protonpack.StreamUtils;
import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import javaslang.Tuple;
import javaslang.control.Try;
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
import java.util.Arrays;
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

    @Pointcut("execution(public java.lang.Object *(..))")
    public void publicMethodReturningObject() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingAnnotated() {
    }

    @Around("publicMethodReturningObject() && requestMappingAnnotated()")
    public ResponseForm executeControllerCode(ProceedingJoinPoint joinPoint) throws Throwable {
        validateRequestBodyIfApplicable(joinPoint);

        return Try.of(joinPoint::proceed)
                .map(returnObject ->
                        ResponseForm.builder()
                                .status(ResponseStatus.OK)
                                .data(returnObject)
                                .build()
                )
                .getOrElseThrow(
                        exc -> exc
                );
    }

    private void validateRequestBodyIfApplicable(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
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
        return StreamUtils.zip(
                Arrays.stream(arguments),
                Arrays.stream(annotationsOverArguments),
                (argument, annotations) -> Tuple.of(
                        argument,
                        Arrays.stream(annotations)
                                .anyMatch(annotation -> annotation instanceof RequestBody)
                ))
                .filter(argumentAndAnnotationExistenceTuple -> argumentAndAnnotationExistenceTuple._2 == Boolean.TRUE)
                .map(argumentAndAnnotationExistenceTuple -> argumentAndAnnotationExistenceTuple._1)
                .findFirst();
    }

    private Annotation[][] getAnnotationsFromMethodParameters(MethodSignature method) throws NoSuchMethodException {
        return method.getDeclaringType()
                .getMethod(method.getName(), method.getParameterTypes())
                .getParameterAnnotations();
    }
}
