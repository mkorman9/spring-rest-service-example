package com.github.mkorman9.web.controller.util;

import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

import static javaslang.API.*;
import static javaslang.Predicates.instanceOf;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
@Slf4j
public class ExceptionCatcher {
    private static final String INTERNAL_ERROR_RESPONSE_TEXT = "Internal error while processing request";
    private static final String UNREADABLE_BODY = "Message body cannot be interpreted as valid JSON document";

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        decideOnLoggingException(exception).ifPresent(exc -> log.error("Error during request processing: ", exc));

        return ResponseEntity
                .status(resolveResponseStatus(exception))
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(resolveResponseObject(exception))
                        .build());
    }

    private HttpStatus resolveResponseStatus(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), HttpStatus.BAD_REQUEST),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), HttpStatus.BAD_REQUEST),
                Case(instanceOf(HttpMessageNotReadableException.class), HttpStatus.BAD_REQUEST),
                Case($(), HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

    private Object resolveResponseObject(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), () -> ((InvalidInputDataException) exception).getError()),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), ResponseError.builder().message(exception.getMessage()).build()),
                Case(instanceOf(HttpMessageNotReadableException.class), ResponseError.builder().message(UNREADABLE_BODY).build()),
                Case($(), ResponseError.builder().message(INTERNAL_ERROR_RESPONSE_TEXT).build())
        );
    }

    private Optional<Exception> decideOnLoggingException(Exception exception) {
        return Match(exception).of(
                Case(instanceOf(InvalidInputDataException.class), Optional.empty()),
                Case(instanceOf(MethodArgumentTypeMismatchException.class), Optional.empty()),
                Case(instanceOf(HttpMessageNotReadableException.class), Optional.empty()),
                Case($(), Optional.of(exception))
        );
    }
}