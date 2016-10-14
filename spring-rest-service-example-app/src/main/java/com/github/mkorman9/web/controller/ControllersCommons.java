package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.github.mkorman9.web.controller")
@Slf4j
class ControllersCommons {
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        log.error("Error during request processing", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseForm.builder()
                        .status(ResponseStatus.ERROR)
                        .errors(ImmutableList.of(ResponseError.builder()
                                .message(exception.getMessage())
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
}
