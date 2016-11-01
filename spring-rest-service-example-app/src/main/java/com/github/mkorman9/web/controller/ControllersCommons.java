package com.github.mkorman9.web.controller;

import com.github.mkorman9.logic.exception.InvalidInputDataException;
import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
@Slf4j
class ControllersCommons {
    private static final String INTERNAL_ERROR_RESPONSE_TEXT = "Internal error while processing data";

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        log.error("Error during request processing", exception);

        return ResponseEntity
                .status(resolveResponseStatus(exception))
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .errors(ImmutableList.of(ResponseError.builder()
                                .message(resolveResponseText(exception))
                                .build()
                        ))
                        .build());
    }

    protected ResponseForm handleBindingError(BindingResult bindingResult) {
        return ResponseForm.builder()
                .status(ResponseStatus.ERROR)
                .errors(bindingResult.getFieldErrors().stream()
                        .map(error -> ResponseError.builder()
                                .message(error.getCode())
                                .field(error.getField())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }

    private HttpStatus resolveResponseStatus(Exception exception) {
        return exception instanceof InvalidInputDataException ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String resolveResponseText(Exception exception) {
        return exception instanceof InvalidInputDataException ?
                exception.getMessage() :
                INTERNAL_ERROR_RESPONSE_TEXT;
    }
}
