package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.response.ResponseError;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
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
class ControllersCommons {
    private static Logger LOGGER = LoggerFactory.getLogger(ControllersCommons.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        LOGGER.error("Error during request processing", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseForm.build(ResponseStatus.ERROR)
                        .withError(ResponseError.build(exception.getMessage()).get())
                        .get());
    }

    protected ResponseForm handleBindingError(BindingResult bindingResult) {
        return ResponseForm.build(ResponseStatus.ERROR)
                .withErrors(bindingResult.getFieldErrors().stream()
                        .map(error -> ResponseError.build(error.getCode()).refersToField(error.getField()).get())
                        .collect(Collectors.toList()))
                .get();
    }
}
