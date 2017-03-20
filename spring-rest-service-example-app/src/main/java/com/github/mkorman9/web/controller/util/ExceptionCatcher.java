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

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity invalidInputDataExceptionHandler(InvalidInputDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(exception.getError())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(ResponseError.builder().message(exception.getMessage()).build())
                        .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(ResponseError.builder().message(UNREADABLE_BODY).build())
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        log.error("Error during request processing: ", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .error(ResponseError.builder().message(INTERNAL_ERROR_RESPONSE_TEXT).build())
                        .build()
                );
    }
}
